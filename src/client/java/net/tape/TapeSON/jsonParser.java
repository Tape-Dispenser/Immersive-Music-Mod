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

    public String parseString(int index) {
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

    public String parseNumber(int index) {

    }

    public jsonValue parseValue(int index) {
        String val;
        String type;

        char temp = this.JSON.charAt(index);

        switch (temp) {
            case '{':
                type = "object";
                // parse object
            case '[':
                type = "array";
                // parse array
            case '"':
                type = "string";
                // parse string
            case 't':
                type = "constant";
                // parse true
            case 'f':
                type = "constant";
                // parse false
            case 'n':
                type = "constant";
                // parse null
            default:
                type = "number";
                // parse number


        }
    }

    public String parseWhitespace(int index) {
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

    public String parseObject(int index) {
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


    public Map<String, String> getKeyPairs() {

        // variable initialization
        boolean inList = false;
        boolean inObject = false;
        boolean isKey = true;

        int leftCurlies = 0;
        int rightCurlies = 0;
        int leftSquares = 0;
        int rightSquares = 0;

        String value;
        String key;
        String temp;
        String expectedType = "string";

        Map<String, String> pairs = new HashMap<>() {};


        int i = 0;
        while (i < this.JSON.length()) {
            char c = this.JSON.charAt(i);

            switch (expectedType) {
                case "string":
                    // string in this case is a key value and should have a ':' after it
                    key = parseString(i);
                    i = i + key.length() + 1;

                    temp = parseWhitespace(i);
                    i = i + temp.length() + 1;

                    if (this.JSON.charAt(i) != ':') {
                        throw new jsonParseException(String.format("Syntax Error: missing semicolon at index %d while parsing key value", i));
                    }

                    temp = parseWhitespace(++i);
                    i = i + temp.length() + 1;
                    expectedType = "value";
                    break;


                case "whitespace":
                    // either a space, linefeed, carriage return, or horizontal tab
                case "value":
                    // either a string, number, object, or array, or literal true, false, or null
                case "end":
                    // either a ',' or a '}'
            }


        }
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
