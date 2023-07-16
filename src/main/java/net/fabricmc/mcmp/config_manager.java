package net.fabricmc.mcmp;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.JsonDataLoader;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import static java.nio.file.Files.readAllLines;



public class config_manager {
    static HashMap<String, Vector<String>> biomes = new HashMap<String, Vector<String>>();
    static Path config_dir = FabricLoader.getInstance().getConfigDir();

    static Vector<String> lines = new Vector<String>();

    public static void init() {
        // read config file
        try {
            String config_file = config_dir.toString().concat("/mcmp/biome_playlists.json");
            lines = new Vector<String> (readAllLines(Paths.get(config_file), StandardCharsets.UTF_8));
        }
        catch (IOException e) {
            MCMP_main.LOGGER.info("Config file/directory not found, creating now");
            first_launch();
            String config_file = config_dir.toString().concat("/mcmp/biome_playlists.json");
            try {
                lines = new Vector<String> (readAllLines(Paths.get(config_file), StandardCharsets.UTF_8));
            }
            catch (IOException f) {
                MCMP_main.LOGGER.error("Failed to create biome_playlists.json");
                throw new RuntimeException(f);
            }
        }
        for (String line : lines) {
            line = line.strip();
            if (!Objects.equals(line, "{") && !Objects.equals(line, "}") && !Objects.equals(line, "")) {

                String[] splits = line.split("\":");
                if (splits.length != 2) {
                    MCMP_main.LOGGER.error("Invalid biome_playlists.json!"); // let player know how to reset to defaults eventually
                    throw new RuntimeException();
                }
                String key = splits[0].strip().replaceAll("\"", "");

                Vector<String> pair = new Vector<String>();
                Collections.addAll(pair, (splits[1].strip().replaceAll("\\]|\\[","").split(","))); // java regex my beloved (warning included)
                biomes.put(key,pair);
            }
        }
        MCMP_main.LOGGER.info(biomes.toString());


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
            if (f.createNewFile()) {
                // load initial values
                FileWriter writer = new FileWriter(f.getPath()); // i need to find out how to compile data into a json file that i can load into registry
                writer.write("{\n");
                writer.write("  \"fallback\": [\"vanilla\"],\n");
                writer.write("  \"menu\": [\"mcmp:menu-01\", \"mcmp:menu-02\"],\n");
                writer.write("  \"creative\": [],\n");
                writer.write("  \"vanilla\": [],\n");
                writer.write("  \"minecraft:the_void\": [],\n");
                writer.write("  \"minecraft:plains\": [\"mcmp:plains-01\"],\n");
                writer.write("  \"minecraft:sunflower_plains\": [],\n");
                writer.write("  \"minecraft:snowy_plains\": [\"mcmp:snow_plains-01\"],\n");
                writer.write("  \"minecraft:ice_spikes\": [],\n");
                writer.write("  \"minecraft:desert\": [\"mcmp:desert-01\"],\n");
                writer.write("  \"minecraft:swamp\": [],\n");
                writer.write("  \"minecraft:mangrove_swamp\": [],\n");
                writer.write("  \"minecraft:forest\": [\"mcmp:forest-01\"],\n");
                writer.write("  \"minecraft:flower_forest\": [],\n");
                writer.write("  \"minecraft:birch_forest\": [\"mcmp:birch_forest-01\", \"mcmp:birch_forest-02\"],\n");
                writer.write("  \"minecraft:dark_forest\": [\"mcmp:dark_oak_forest-01\"],\n");
                writer.write("  \"minecraft:old_growth_birch_forest\": [],\n");
                writer.write("  \"minecraft:old_growth_pine_taiga\": [],\n");
                writer.write("  \"minecraft:old_growth_spruce_taiga\": [],\n");
                writer.write("  \"minecraft:taiga\": [\"mcmp:taiga-01\", \"mcmp:taiga-02\"],\n");
                writer.write("  \"minecraft:snowy_taiga\": [],\n");
                writer.write("  \"minecraft:savanna\": [],\n");
                writer.write("  \"minecraft:savanna_plateau\": [],\n");
                writer.write("  \"minecraft:windswept_hills\": [],\n");
                writer.write("  \"minecraft:windswept_gravelly_hills\": [],\n");
                writer.write("  \"minecraft:windswept_forest\": [],\n");
                writer.write("  \"minecraft:windswept_savanna\": [],\n");
                writer.write("  \"minecraft:jungle\": [\"mcmp:jungle-01\"],\n");
                writer.write("  \"minecraft:sparse_jungle\": [],\n");
                writer.write("  \"minecraft:bamboo_jungle\": [],\n");
                writer.write("  \"minecraft:badlands\": [],\n");
                writer.write("  \"minecraft:eroded_badlands\": [],\n");
                writer.write("  \"minecraft:wooded_badlands\": [],\n");
                writer.write("  \"minecraft:meadow\": [\"mcmp:mountains_meadows-01\"],\n");
                writer.write("  \"minecraft:cherry_grove\": [\"mcmp:cherry_forest-01\"],\n");
                writer.write("  \"minecraft:grove\": [],\n");
                writer.write("  \"minecraft:snowy_slopes\": [\"mcmp:mountain-01\"],\n");
                writer.write("  \"minecraft:frozen_peaks\": [\"mcmp:mountain-01\"],\n");
                writer.write("  \"minecraft:jagged_peaks\": [\"mcmp:mountain-01\"],\n");
                writer.write("  \"minecraft:stony_peaks\": [\"mcmp:mountain-01\"],\n");
                writer.write("  \"minecraft:river\": [],\n");
                writer.write("  \"minecraft:frozen_river\": [],\n");
                writer.write("  \"minecraft:beach\": [],\n");
                writer.write("  \"minecraft:snowy_beach\": [],\n");
                writer.write("  \"minecraft:stony_shore\": [],\n");
                writer.write("  \"minecraft:warm_ocean\": [\"mcmp:warm_ocean-01\"],\n");
                writer.write("  \"minecraft:lukewarm_ocean\": [],\n");
                writer.write("  \"minecraft:deep_lukewarm_ocean\": [],\n");
                writer.write("  \"minecraft:ocean\": [],\n");
                writer.write("  \"minecraft:deep_ocean\": [],\n");
                writer.write("  \"minecraft:cold_ocean\": [],\n");
                writer.write("  \"minecraft:deep_cold_ocean\": [],\n");
                writer.write("  \"minecraft:frozen_ocean\": [],\n");
                writer.write("  \"minecraft:deep_frozen_ocean\": [],\n");
                writer.write("  \"minecraft:mushroom_fields\": [\"mcmp:mushroom_island-01\"],\n");
                writer.write("  \"minecraft:dripstone_caves\": [],\n");
                writer.write("  \"minecraft:lush_caves\": [\"mcmp:lush_caves-01\", \"mcmp:lush_caves-02\"],\n");
                writer.write("  \"minecraft:deep_dark\": [\"mcmp:deep_dark-01\"],\n");
                writer.write("  \"minecraft:nether_wastes\": [\"mcmp:nether_wastes-01\"],\n");
                writer.write("  \"minecraft:warped_forest\": [],\n");
                writer.write("  \"minecraft:crimson_forest\": [],\n");
                writer.write("  \"minecraft:soul_sand_valley\": [\"mcmp:soul_sand_valley-01\"],\n");
                writer.write("  \"minecraft:basalt_deltas\": [\"mcmp:basalt_deltas-01\"],\n");
                writer.write("  \"minecraft:the_end\": [],\n");
                writer.write("  \"minecraft:end_highlands\": [],\n");
                writer.write("  \"minecraft:end_midlands\": [],\n");
                writer.write("  \"minecraft:small_end_islands\": [],\n");
                writer.write("  \"minecraft:end_barrens\": []\n");
                writer.write("}\n");

                writer.close();
            }
        } catch (IOException e) {
            MCMP_main.LOGGER.error("uhhh something went really wrong...");
            throw new RuntimeException(e);
        }

    }

}