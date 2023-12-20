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
        String content;
        try {
            String config_file = config_dir.toString().concat("/mcmp/biome_playlists.json");
            content = new String(readAllBytes(Paths.get(config_file)));
            MCMP_main.LOGGER.info(content);
        }
        catch (IOException e) {
            MCMP_main.LOGGER.info("Config file/directory not found, creating now");
            first_launch();
            String config_file = config_dir.toString().concat("/mcmp/biome_playlists.json");
            try {
                content = new String (readAllBytes(Paths.get(config_file)));
                MCMP_main.LOGGER.info(content);
            }
            catch (IOException f) {
                MCMP_main.LOGGER.error("Failed to create biome_playlists.json");
                throw new RuntimeException(f);
            }
        }


        // parse config file
        Gson gson = new Gson();
        TypeToken<Map<String, String[]>> mapType = new TypeToken<Map<String, String[]>>(){}; // java my beloved (this line isn't even that bad)

        biome_playlists.biomePlaylists = gson.fromJson(content, mapType);
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

        MCMP_main.LOGGER.info("Resources Successfully Registered");
    }

    public static void first_launch() {
        // create mcmp directory in .minecraft/config and add biome_playlists.json
        try {
            Files.createDirectories(Paths.get(config_dir.toString().concat("/mcmp")));
        } catch (IOException e) {
            MCMP_main.LOGGER.error("cannot find .minecraft/config directory!");
            throw new RuntimeException(e);
        }

        // create biome_playlists.json if it does not exist
        File f = new File(config_dir.toString().concat("/mcmp/biome_playlists.json"));
        try {

            Gson gson = new GsonBuilder()
                    .enableComplexMapKeySerialization()
                    .create();

            String jsonString = gson.toJson(biome_playlists.defaultPlaylists);
            MCMP_main.LOGGER.info(biome_playlists.defaultPlaylists.toString());

            MCMP_main.LOGGER.info(jsonString);

            FileWriter writer = new FileWriter(f.getPath());
            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            MCMP_main.LOGGER.error("uhhh something went really wrong...");
            throw new RuntimeException(e);
        }

    }

}