package net.tape.timm.util;

import net.tape.timm.timmMain;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static oshi.util.FileUtil.readFile;

public class jsonParser {

    private String JSON;

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

        for (String match : matches) {
            timmMain.LOGGER.info(match);
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
}
