package net.tape.timm;


import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.parseBoolean;


public class modConfig {
    public static Map<String, String> configMap = new HashMap<>();

    public static final Map<String, String> defaultConfig = new HashMap<>();

    public static long minGameDelay;
    public static long maxGameDelay;
    public static long minMenuDelay;
    public static long maxMenuDelay;
    public static boolean debugLogging;
    public static boolean singlePlaylist;

    public static void init() {
        defaultConfig.put("debug", "false");

        // menu delays
        defaultConfig.put("menuMinDelay", "20");
        defaultConfig.put("menuMaxDelay", "600");

        // game delay                                                          TODO: eventually songs need their own individual delays
        defaultConfig.put("gameMinDelay", "1200");
        defaultConfig.put("gameMaxDelay", "12000");

        defaultConfig.put("singlePlaylist", "false");
    }

    public static void copyVals() {
        minGameDelay = Integer.parseInt(configMap.get("gameMinDelay")); // get the second index of the list and parse as integer.
        maxGameDelay = Integer.parseInt(configMap.get("gameMaxDelay"));

        minMenuDelay = Integer.parseInt(configMap.get("menuMinDelay")); // get the second index of the list and parse as integer.
        maxMenuDelay = Integer.parseInt(configMap.get("menuMaxDelay"));

        debugLogging = parseBoolean(configMap.get("debug"));

        singlePlaylist = parseBoolean(configMap.get("singlePlaylist"));
    }
}