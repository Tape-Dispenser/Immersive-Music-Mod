package net.tape.timm;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;



import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.readAllLines;

import net.tape.timm.audio.biomePlaylists;


public class configManager {

    public static String config_dir = FabricLoader.getInstance().getConfigDir().toString();
    public static final File timmCfgDir = new File(FabricLoader.getInstance().getConfigDir().toFile(), "timm");
    public static final File musicDir = new File(FabricLoader.getInstance().getGameDir().toFile(), "music");
    public static final File timmMusicDir = new File(musicDir, "TIMM");
    public static final File timmCfgFile = new File(timmCfgDir, "TIMM.config");

    public static void init() {
        // read config file
        String cfgContent;

        if (!musicDir.isDirectory()) {
            try {
                Files.createDirectories(musicDir.toPath());
                if (modConfig.debugLogging) {
                    timmMain.LOGGER.info(String.format("successfully created config directory at %s", musicDir.getPath()));}
            } catch (IOException e) {
                timmMain.LOGGER.error("Failed to create .minecraft/music folder!");
                timmMain.LOGGER.warn("This may be because .minecraft does not exist, or because of some permissions issue.");
                throw new RuntimeException(e);
            }
        }

        if (!timmMusicDir.isDirectory()) {
            try {
                Files.createDirectories(timmMusicDir.toPath());
                if (modConfig.debugLogging) {
                    timmMain.LOGGER.info("successfully created config directory at {}", timmMusicDir.getPath());}
            } catch (IOException e) {
                timmMain.LOGGER.error("Failed to create .minecraft/music/TIMM folder!");
                timmMain.LOGGER.warn("This may be because .minecraft/music does not exist, or because of some permissions issue.");
                throw new RuntimeException(e);
            }
        }

        try {

            cfgContent = Files.readString(timmCfgFile.toPath());

            if (modConfig.debugLogging) {timmMain.LOGGER.info(cfgContent);}

        } catch (IOException e) {
            // config file doesn't exist, create it now
            if (modConfig.debugLogging) {timmMain.LOGGER.info("Main Config file not found, creating now...");}
            first_launch();
            try {
                cfgContent = Files.readString(timmCfgFile.toPath());
                if (modConfig.debugLogging) {timmMain.LOGGER.info(cfgContent);}
            } catch (IOException f) {
                // something went really wrong, likely some permissions issue, or maybe config directory doesn't exist
                timmMain.LOGGER.error("Failed to create TIMM.config");
                throw new RuntimeException(f);
            }
        }

        // parse biome playlists file
        JsonObject obj = JsonParser.parseString(cfgContent).getAsJsonObject();
        boolean rewrite = false;
        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            if (!entry.getValue().isJsonArray()) {
                // simply enter key and value in config map
                modConfig.configMap.put(entry.getKey(), entry.getValue().getAsString());
                continue;
            }
            // copy value to config map, signal we need to rewrite the config file
            modConfig.configMap.put(entry.getKey(), entry.getValue().getAsJsonArray().get(1).getAsString());
            rewrite = true;
        }

        if (rewrite) {
            update_cfg(modConfig.configMap);
        }

        timmMain.LOGGER.info("Resources Successfully Registered");
    }

    public static void update_cfg(Map<String, String> inputMap) {
        File f = new File(String.format("%s/timm/TIMM.config", config_dir));
        JsonObject newObj = new JsonObject();
        for (Map.Entry<String, String> entry : inputMap.entrySet()) {
            newObj.add(entry.getKey(), new JsonPrimitive(entry.getValue()));
        }

        String configJSON = newObj.toString();

        try {
            FileWriter writer = new FileWriter(f.getPath());
            writer.write(configJSON);
            writer.close();
        } catch (IOException e) {
            timmMain.LOGGER.error("Error");
            throw new RuntimeException(e);
        }
    }


    private static void first_launch() {
        // create timm directory in .minecraft/config and add biome_playlists.json

        if (timmCfgFile.isFile()) {
            return;
        }

        if (!timmCfgDir.isDirectory()) {
            try {
                Files.createDirectories(Paths.get(config_dir.concat("/timm")));
                if (modConfig.debugLogging) {timmMain.LOGGER.info(String.format("successfully created config directory at %s", timmCfgDir.getPath()));}
            } catch (IOException e) {
                timmMain.LOGGER.error("Failed to create .minecraft/config/timm folder!");
                timmMain.LOGGER.warn("This may be because .minecraft/config does not exist, or because of some permissions issue.");
                throw new RuntimeException(e);
            }
        }

        update_cfg(modConfig.defaultConfig);
    }

}