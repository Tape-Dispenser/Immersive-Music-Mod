package net.fabricmc.mcmp;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;


import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.readAllLines;

import com.google.gson.Gson;


public class config_manager {

    static Path config_dir = FabricLoader.getInstance().getConfigDir();

    public static void init() {
        // initialize variables


        // read config file

        // read biome playlists
        String PLcontent;
        try {
            String pl_file = config_dir.toString().concat("/mcmp/biome_playlists.json");
            PLcontent = new String(readAllBytes(Paths.get(pl_file)));

            if (MCMP_main.debugLogging) {MCMP_main.LOGGER.info(PLcontent);}

        }
        catch (IOException e) {
            MCMP_main.LOGGER.info("Biome playlists file/directory not found, creating now...");
            first_launch();
            String pl_file = config_dir.toString().concat("/mcmp/biome_playlists.json");
            try {
                PLcontent = new String (readAllBytes(Paths.get(pl_file)));

                if (MCMP_main.debugLogging) {
                    MCMP_main.LOGGER.info(PLcontent);
                }
            }
            catch (IOException f) {
                MCMP_main.LOGGER.error("Failed to create biome_playlists.json");
                throw new RuntimeException(f);
            }
        }

        String cfgContent;
        try {
            String cfg_file = config_dir.toString().concat("/mcmp/TIMM.config");
            cfgContent = new String(readAllBytes(Paths.get(cfg_file)));

            if (MCMP_main.debugLogging) {MCMP_main.LOGGER.info(cfgContent);}
        }
        catch (IOException e) {
            MCMP_main.LOGGER.info("Main Config file not found, creating now...");
            first_launch();
            String cfg_file = config_dir.toString().concat("/mcmp/TIMM.config");
            try {
                cfgContent = new String (readAllBytes(Paths.get(cfg_file)));

                if (MCMP_main.debugLogging) {
                    MCMP_main.LOGGER.info(cfgContent);
                }
            }
            catch (IOException f) {
                MCMP_main.LOGGER.error("Failed to create TIMM.config");
                throw new RuntimeException(f);
            }
        }



        // parse biome playlists file
        Gson gson = new Gson();
        TypeToken<Map<String, String[]>> mapType = new TypeToken<Map<String, String[]>>(){}; // java my beloved (this line isn't even that bad)

        biome_playlists.biomePlaylists = gson.fromJson(PLcontent, mapType);

        String[] currentVersionPair = biome_playlists.biomePlaylists.get("version");
        if (currentVersionPair == null) {
            MCMP_main.LOGGER.error("Current biome_playlists.json version is using an outdated format!");
            MCMP_main.LOGGER.warn("To update to most recent version of biome_playlists, simply delete your current one.");
            throw new RuntimeException("Outdated biome_playlists.json");
        }
        int currentVersion = Integer.parseInt(Arrays.asList(currentVersionPair).get(0));
        int defaultVersion = Integer.parseInt(Arrays.asList(biome_playlists.defaultPlaylists.get("version")).get(0));

        if (defaultVersion > currentVersion) {
            MCMP_main.LOGGER.warn("Warning: Current biome_playlists.json version is behind default, consider updating!");
            MCMP_main.LOGGER.warn("To update to most recent version of biome_playlists, simply delete your current one.");
        }


        // parse config file
        mod_config.configMap = gson.fromJson(cfgContent, mapType);


        MCMP_main.LOGGER.info("Resources Successfully Registered");


    }

    public static void first_launch() {
        // create mcmp directory in .minecraft/config and add biome_playlists.json
        if (!Files.isDirectory(Paths.get(config_dir.toString().concat("/mcmp/")))) {
            try {
                Files.createDirectories(Paths.get(config_dir.toString().concat("/mcmp")));
                if (MCMP_main.debugLogging) {
                    MCMP_main.LOGGER.info("created directory ".concat(config_dir.toString().concat("/mcmp")) );
                }
            } catch (IOException e) {
                MCMP_main.LOGGER.error("Failed to create .minecraft/config/mcmp folder!");
                MCMP_main.LOGGER.warn("This may be because .minecraft/config does not exist, or because of some permissions issue.");
                throw new RuntimeException(e);
            }
        }

        if (!Files.isDirectory(Paths.get(config_dir.toString().concat("/mcmp/biome_playlists.json")))) {
            // create biome_playlists.json if it does not exist
            File f = new File(config_dir.toString().concat("/mcmp/biome_playlists.json"));
            Gson gson = new GsonBuilder()
                    .enableComplexMapKeySerialization()
                    .create();

            String playlistJSON = gson.toJson(biome_playlists.defaultPlaylists);

            //debug
            if (MCMP_main.debugLogging) {
                MCMP_main.LOGGER.info(biome_playlists.defaultPlaylists.toString());
                MCMP_main.LOGGER.info(playlistJSON);
            }

            try {
                FileWriter writer = new FileWriter(f.getPath());
                writer.write(playlistJSON);
                writer.close();

                //debug
                if (MCMP_main.debugLogging) {
                    MCMP_main.LOGGER.info("created file ".concat(config_dir.toString().concat("/mcmp/biome_playlists.json")) );
                }

            } catch (IOException e) {
                MCMP_main.LOGGER.error("Something went wrong while trying to create default playlists.");
                throw new RuntimeException(e);
            }
        }

        if (!Files.isDirectory(Paths.get(config_dir.toString().concat("/mcmp/TIMM.config")))) {
            File f = new File(config_dir.toString().concat("/mcmp/TIMM.config"));
            Gson gson = new GsonBuilder().create();

            String configJSON = gson.toJson(mod_config.defaultConfig);

            if (MCMP_main.debugLogging) {
                MCMP_main.LOGGER.info(biome_playlists.defaultPlaylists.toString());
                MCMP_main.LOGGER.info(configJSON);
            }

            try {
                FileWriter writer = new FileWriter(f.getPath());
                writer.write(configJSON);
                writer.close();

            } catch (IOException e) {
                MCMP_main.LOGGER.error("Something went wrong while trying to create default config.");
                throw new RuntimeException(e);
            }

        }


    }
}