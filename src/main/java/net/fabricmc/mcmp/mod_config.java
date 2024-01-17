package net.fabricmc.mcmp;


import java.util.HashMap;
import java.util.Map;


public class mod_config {
    public static Map<String, String[]> configMap = new HashMap<String, String[]>();

    public static final Map<String, String[]> defaultConfig = new HashMap<String, String[]>();

    public static void init() {
        defaultConfig.put("debug", new String[]{"bool", "true"}); // i store the type of the rest of the values so i can have multiple types in config


        // menu delays
        defaultConfig.put("menuMinDelay", new String[]{"u32", "20" /* delay times are in ticks (20ths of a second) */ });
        defaultConfig.put("menuMaxDelay", new String[]{"u32", "600" });

        // game delay                                                          TODO: eventually songs need their own individual delays
        defaultConfig.put("gameMinDelay", new String[]{"u32", "1200"});
        defaultConfig.put("gameMaxDelay", new String[]{"u32", "12000"});

    }
}