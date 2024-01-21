package net.fabricmc.mcmp;


import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.parseBoolean;


public class mod_config {
    public static Map<String, String[]> configMap = new HashMap<String, String[]>();

    public static final Map<String, String[]> defaultConfig = new HashMap<String, String[]>();

    public static int minSongDelay;
    public static int maxSongDelay;
    public static int minMenuDelay;
    public static int maxMenuDelay;
    public static boolean debugLogging;

    public static void init() {
        defaultConfig.put("debug", new String[]{"bool", "true"}); // i store the type of the rest of the values so i can have multiple types in config


        // menu delays
        defaultConfig.put("menuMinDelay", new String[]{"u32", "20" /* delay times are in ticks (20ths of a second) */ });
        defaultConfig.put("menuMaxDelay", new String[]{"u32", "600" });

        // game delay                                                          TODO: eventually songs need their own individual delays
        defaultConfig.put("gameMinDelay", new String[]{"u32", "1200"});
        defaultConfig.put("gameMaxDelay", new String[]{"u32", "12000"});

    }

    public static void copyVals() {
        minSongDelay = Integer.parseInt(configMap.get("gameMinDelay")[1]); // get the second index of the list and parse as integer.
        maxSongDelay = Integer.parseInt(configMap.get("gameMaxDelay")[1]);

        minMenuDelay = Integer.parseInt(configMap.get("menuMinDelay")[1]); // get the second index of the list and parse as integer.
        maxMenuDelay = Integer.parseInt(configMap.get("menuMaxDelay")[1]);

        debugLogging = parseBoolean(configMap.get("debug")[1]);






    }
}