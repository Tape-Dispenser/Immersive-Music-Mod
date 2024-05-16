package net.tape.TapeSON;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static oshi.util.FileUtil.readFile;

public class jsonParser {

    private final String JSON;

    public jsonParser(String filePath) {

        // import file contents as string

        // get all lines in file
        List<String> lines = readFile(filePath);
        // initialize string
        String jsonString = "";
        for (String line : lines) {
            // append all lines to string
            jsonString = jsonString.concat(line);
        }

        // get master json object
        List<String> matches = getMatches("(?s)\\{.*\\}", jsonString);
        if (matches.size() == 1) {
            String match = matches.get(0);
            this.JSON = match.substring(1, match.length()-1);
        } else {
            throw new jsonParseException(String.format("Error Parsing JSON, expected one master JSON object but instead got %d", matches.size()));
        }
    }

    public String skipWhitespace(int index) {
        String val = "";
        while (index < this.JSON.length()) {
            Character c = this.JSON.charAt(index);
            switch (c) {
                case ' ','\n','\r','\t':
                    val = val.concat(String.valueOf(c));
                    break;
                default:
                    return val;
            }
            index++;
        }
        return val;
    }

    public jsonValue lexValue(int index) {
        String val;
        String type;

        char temp = this.JSON.charAt(index);

        switch (temp) {
            case '{':
                type = "object";
                val = lexObject(index);
                break;
            case '[':
                type = "array";
                val = lexArray(index);
                break;
            case '"':
                type = "string";
                val = lexString(index);
                break;
            case 't','f','n':
                type = "constant";
                val = lexConstant(index);
            default:
                type = "number";
                val = lexNumber(index);
                break;
        }
        return new jsonValue(type, val);
    }

    public String lexString(int index) {
        int start = index;
        String key = "";

        // parse character by character until i reach end of the string
        while (index < this.JSON.length()) {
            char c = this.JSON.charAt(index);
            switch (c) {
                // all characters are valid except for control characters, '\', '"'
                // escape codes are also valid
                case '\\':
                    // check escape code and make sure it's valid
                    char nextChar = this.JSON.charAt(++index);
                    switch (nextChar) {
                        case '"':
                            key = key.concat("\"");
                            break;
                        case '\\':
                            key = key.concat("\\");
                            break;
                        case '/':
                            key = key.concat("/");
                            break;
                        case 'b':
                            key = key.concat("\b");
                            break;
                        case 'f':
                            key = key.concat("\f");
                            break;
                        case 'n':
                            key = key.concat("\n");
                            break;
                        case 'r':
                            key = key.concat("\r");
                            break;
                        case 't':
                            key = key.concat("\t");
                            break;
                        case 'u':
                            // get next four characters
                            String hexString = this.JSON.substring(index+1, index+4);
                            // convert string of hex digits to an integer
                            int codePoint = Integer.parseInt(hexString, 16);
                            // parse integer as characters
                            char[] checkChar = Character.toChars(codePoint);
                            // add characters to output string
                            key = key.concat(String.valueOf(checkChar));
                            // update index
                            index = index+4;
                            break;
                        default:
                            throw new jsonParseException(String.format("Syntax Error: Invalid escape character \"\\%c\" at index %d", nextChar, index));

                    }

                case '"':
                    // closing quotation mark, end of string
                    return key;

                default:
                    // make sure character isn't a control character
                    if ((c <= 0x1F) | (0x7F <= c && c <= 0x9F)) {
                        throw new jsonParseException(String.format("Syntax Error: Invalid string character at index %d", index));
                    }
                    // add character to string
                    key = key.concat(String.valueOf(c));

            }

            index++;
        }
        throw new jsonParseException(String.format("Syntax Error: Unclosed string starting at index %d", start));
    }

    public String lexNumber(int index) {
        String val = "";

        val = getMatch("(?<![eE])-?(([1-9][0-9]*)|(0))(\\.[0-9]+)?([eE][+-]?[0-9]+)?", this.JSON.substring(index));
        int newIndex;
        try {
            newIndex = index + val.length();
        } catch (NullPointerException e) {
            throw new jsonParseException(String.format("Unrecognized value starting at index %d", index), e);
        }
        switch (this.JSON.charAt(newIndex)) {
            case ' ','\n','\r','\t',',','}',']':
                break;
            default:
                throw new jsonParseException(String.format("Unrecognized value starting at index %d", index));
        }
        return val;
    }

    public String lexConstant(int index) {
        int start = index;
        String val = "";

        while (index < this.JSON.length()) {
            char c = this.JSON.charAt(index);
            val = val.concat(String.valueOf(c));

            switch (val) {
                case "true", "false", "null":
                    return val;
            }

            if (index-start > 4) {
                throw new jsonParseException(String.format("Unrecognized Constant \"%s\" starting at index %d", val, start));
            }

            index++;
        }
        throw new jsonParseException(String.format("Unrecognized Constant \"%s\" starting at index %d", val, start));
    }



    public String lexObject(int index) {
        // return first found object starting at specified index
        int start = index;
        int leftBraces = 0;
        int rightBraces = 0;

        String val = "";

        while (index < this.JSON.length()) {
            char c = this.JSON.charAt(index);
            switch (c) {
                case '{':
                    leftBraces += 1;
                    val = val.concat(String.valueOf(c));
                    break;
                case '}':
                    rightBraces += 1;
                    val = val.concat(String.valueOf(c));
                    if (leftBraces == rightBraces) {
                        return val;
                    }
                    break;
                default:
                    val = val.concat(String.valueOf(c));
                    break;
            }
            index++;
        }
        throw new jsonParseException(String.format("Syntax Error: Unclosed JSON Object starting at index %d", start));
    }

    public String lexArray(int index) {
        // return first found object starting at specified index
        int start = index;
        int leftBraces = 0;
        int rightBraces = 0;

        String val = "";
        while (index < this.JSON.length()) {
            char c = this.JSON.charAt(index);
            val = val.concat(String.valueOf(c));
            switch (c) {
                case '[':
                    leftBraces += 1;
                    break;
                case ']':
                    rightBraces += 1;
                    if (leftBraces == rightBraces) {
                        return val;
                    }
                    break;
                default:
                    break;
            }
            index++;
        }
        throw new jsonParseException(String.format("Syntax Error: Unclosed Array starting at index %d", start));
    }


    public Map<String, jsonValue> getKeyPairs() {

        // variable initialization
        jsonValue value;
        String key = null;
        String temp;
        String expectedType = "string";

        Map<String, jsonValue> pairs = new HashMap<>() {};


        int i = 0;
        while (i < this.JSON.length()) {
            char c = this.JSON.charAt(i);

            switch (expectedType) {
                case "string":
                    // string in this case is a key value and should have a ':' after it
                    key = lexString(i);
                    i = i + key.length();

                    temp = skipWhitespace(i);
                    i = i + temp.length();

                    if (this.JSON.charAt(i) != ':') {
                        throw new jsonParseException(String.format("Syntax Error: missing semicolon at index %d while parsing key value", i));
                    }

                    temp = skipWhitespace(i);
                    i = i + temp.length();
                    expectedType = "value";
                    break;

                case "value":
                    value = lexValue(i);
                    i = i + value.getSource().length();

                    if (key == null) {
                        throw new jsonParseException("to be completely honest idk what went wrong here maybe you forgor the key?");
                    }

                    if (pairs.containsKey(key)) {
                        throw new jsonParseException(String.format("Duplicate keys \"%s\"", key));
                    }

                    pairs.put(key, value);

                    temp = skipWhitespace(i);
                    i += temp.length();

                    key = null;

                    // parse comma/bracket



                case "end":
                    // either a ',' or a '}'
            }


        }

        return pairs;
    }



    private List<String> getMatches(String patternString, String inputString) {
        Matcher m = Pattern.compile(patternString)
                .matcher(inputString);
        List<String> allMatches = new ArrayList<String>();
        while (m.find()) {
            allMatches.add(m.group());
        }
        return allMatches;
    }

    private String getMatch(String patternString, String inputString) {
        Matcher m = Pattern.compile(patternString)
                .matcher(inputString);
        if (m.find()) {
            return m.group();
        } else {
            return null;
        }
    }
}
