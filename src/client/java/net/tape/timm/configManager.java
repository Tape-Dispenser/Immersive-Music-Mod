package net.tape.timm;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;



import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.readAllLines;

import com.google.gson.Gson;


public class configManager {

    static Path config_dir = FabricLoader.getInstance().getConfigDir();

    public static void init() {
        // initialize variables


        // read config file

        // read biome playlists
        String PLcontent;
        try {
            String pl_file = config_dir.toString().concat("/timm/biome_playlists.json");
            PLcontent = new String(readAllBytes(Paths.get(pl_file)));

            if (modConfig.debugLogging) {timmMain.LOGGER.info(PLcontent);}

        } catch (IOException e) {
            // biome playlists doesn't exist, create it now
            timmMain.LOGGER.info("Biome playlists file/directory not found, creating now...");
            first_launch();
            String pl_file = config_dir.toString().concat("/timm/biome_playlists.json");
            try {
                PLcontent = new String (readAllBytes(Paths.get(pl_file)));

                if (modConfig.debugLogging) {timmMain.LOGGER.info(PLcontent);}

            } catch (IOException f) {
                // something really wrong happened, either a permissions issue or config directory doesn't exist.
                timmMain.LOGGER.error("Failed to create biome_playlists.json");
                throw new RuntimeException(f);
            }
        }

        // read config file
        String cfgContent;
        try {
            String cfg_file = config_dir.toString().concat("/timm/TIMM.config");
            cfgContent = new String(readAllBytes(Paths.get(cfg_file)));

            if (modConfig.debugLogging) {timmMain.LOGGER.info(cfgContent);}

        } catch (IOException e) {
            // config file doesn't exist, create it now

            if (modConfig.debugLogging) {timmMain.LOGGER.info("Main Config file not found, creating now...");}

            first_launch();
            String cfg_file = config_dir.toString().concat("/timm/TIMM.config");
            try {
                cfgContent = new String (readAllBytes(Paths.get(cfg_file)));

                if (modConfig.debugLogging) {timmMain.LOGGER.info(cfgContent);}

            } catch (IOException f) {
                // something went really wrong, likely some permissions issue, or maybe config directory doesn't exist
                timmMain.LOGGER.error("Failed to create TIMM.config");
                throw new RuntimeException(f);
            }
        }



        // parse biome playlists file
        Gson gson = new Gson();
        TypeToken<Map<String, String[]>> mapType = new TypeToken<Map<String, String[]>>(){}; // java my beloved (this line isn't even that bad)

        biomePlaylists.biomePlaylists = gson.fromJson(PLcontent, mapType);

        String[] currentVersionPair = biomePlaylists.biomePlaylists.get("version");
        if (currentVersionPair == null) {
            timmMain.LOGGER.error("Current biome_playlists.json version is using an outdated format!");
            timmMain.LOGGER.warn("To update to most recent version of biome_playlists, simply delete your current one.");
            // TODO: delete old biome playlist file and replace it with new one instead of crashing the game
            // TODO: maybe read old file and transfer it to the new format?
            throw new RuntimeException("Outdated biome_playlists.json");
        }
        int currentVersion = Integer.parseInt(Arrays.asList(currentVersionPair).get(0));
        int defaultVersion = Integer.parseInt(Arrays.asList(biomePlaylists.defaultPlaylists.get("version")).get(0));

        if (defaultVersion > currentVersion) {
            timmMain.LOGGER.warn("Warning: Current biome_playlists.json version is behind default, consider updating!");
            timmMain.LOGGER.warn("To update to most recent version of biome_playlists, simply delete your current one.");
            // TODO: make a menu/gui option to update biome playlists automatically
        }


        // parse config file
        modConfig.configMap = gson.fromJson(cfgContent, mapType);


        timmMain.LOGGER.info("Resources Successfully Registered");
    }

    public static void update_cfg() {
        File f = new File(config_dir.toString().concat("/timm/TIMM.config"));
        Gson gson = new GsonBuilder().create();

        String configJSON = gson.toJson(modConfig.configMap);

        try {
            FileWriter writer = new FileWriter(f.getPath());
            writer.write(configJSON);
            writer.close();

        } catch (IOException e) {
            timmMain.LOGGER.error("Something went wrong while trying to update config file.");
            throw new RuntimeException(e);
        }

    }


    private static void first_launch() {
        // create timm directory in .minecraft/config and add biome_playlists.json
        if (!Files.isDirectory(Paths.get(config_dir.toString().concat("/timm/")))) {
            try {
                Files.createDirectories(Paths.get(config_dir.toString().concat("/timm")));

                if (modConfig.debugLogging) {timmMain.LOGGER.info("created directory ".concat(config_dir.toString().concat("/timm")) );}

            } catch (IOException e) {
                timmMain.LOGGER.error("Failed to create .minecraft/config/timm folder!");
                timmMain.LOGGER.warn("This may be because .minecraft/config does not exist, or because of some permissions issue.");
                throw new RuntimeException(e);
            }
        }

        if (!Files.isDirectory(Paths.get(config_dir.toString().concat("/timm/biome_playlists.json")))) {
            // create biome_playlists.json if it does not exist
            File f = new File(config_dir.toString().concat("/timm/biome_playlists.json"));
            Gson gson = new GsonBuilder()
                    .enableComplexMapKeySerialization()
                    .create();

            String playlistJSON = gson.toJson(biomePlaylists.defaultPlaylists);

            //debug
            if (modConfig.debugLogging) {
                timmMain.LOGGER.info(biomePlaylists.defaultPlaylists.toString());
                timmMain.LOGGER.info(playlistJSON);
            }

            try {
                FileWriter writer = new FileWriter(f.getPath());
                writer.write(playlistJSON);
                writer.close();

                //debug
                if (modConfig.debugLogging) {
                    timmMain.LOGGER.info("created file ".concat(config_dir.toString().concat("/timm/biome_playlists.json")) );
                }

            } catch (IOException e) {
                timmMain.LOGGER.error("Something went wrong while trying to create default playlists.");
                throw new RuntimeException(e);
            }
        }

        if (!Files.isDirectory(Paths.get(config_dir.toString().concat("/timm/TIMM.config")))) {
            File f = new File(config_dir.toString().concat("/timm/TIMM.config"));
            Gson gson = new GsonBuilder().create();

            String configJSON = gson.toJson(modConfig.defaultConfig);

            if (modConfig.debugLogging) {
                timmMain.LOGGER.info(biomePlaylists.defaultPlaylists.toString());
                timmMain.LOGGER.info(configJSON);
            }

            try {
                FileWriter writer = new FileWriter(f.getPath());
                writer.write(configJSON);
                writer.close();

            } catch (IOException e) {
                timmMain.LOGGER.error("Something went wrong while trying to create default config.");
                throw new RuntimeException(e);
            }

        }


    }
}