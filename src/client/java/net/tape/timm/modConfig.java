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
    public static boolean mixBaseDefault;
    public static boolean superflatMode;
    public static boolean alwaysCheckUpdates;
    public static boolean alwaysGetUpdates;

    public static void init() {
        // menu delays
        defaultConfig.put("menuMinDelay", "20");
        defaultConfig.put("menuMaxDelay", "600");

        // game delay                                                          TODO: eventually songs need their own individual delays
        defaultConfig.put("gameMinDelay", "1200");
        defaultConfig.put("gameMaxDelay", "12000");

        defaultConfig.put("debug", "false");

        // song selection
        defaultConfig.put("singlePlaylist", "false");
        defaultConfig.put("mixBaseDefault", "true");
        defaultConfig.put("superflatMode", "false");

        // updating
        defaultConfig.put("alwaysCheckUpdates", "false");
        defaultConfig.put("alwaysGetUpdates", "false");
    }

    public static void copyVals() {

        // verify all config values are in the config map
        boolean allExist = true;
        for (Map.Entry<String, String> defaultEntry : defaultConfig.entrySet()) {
            String key = defaultEntry.getKey();
            String val = configMap.get(key);
            if (val != null) {
                continue;
            }
            allExist = false;
            configMap.put(key, defaultEntry.getValue());
        }
        if (!allExist) {
            configManager.update_cfg(configMap);
        }

        // copy values from config map to mod config
        minGameDelay = Integer.parseInt(configMap.get("gameMinDelay"));
        maxGameDelay = Integer.parseInt(configMap.get("gameMaxDelay"));

        minMenuDelay = Integer.parseInt(configMap.get("menuMinDelay"));
        maxMenuDelay = Integer.parseInt(configMap.get("menuMaxDelay"));

        debugLogging = parseBoolean(configMap.get("debug"));
        singlePlaylist = parseBoolean(configMap.get("singlePlaylist"));
        mixBaseDefault = parseBoolean(configMap.get("mixBaseDefault"));
        superflatMode = parseBoolean(configMap.get("superflatMode"));

        alwaysCheckUpdates = parseBoolean(configMap.get("alwaysCheckUpdates"));
        alwaysGetUpdates = parseBoolean(configMap.get("alwaysGetUpdates"));
    }
}