package net.fabricmc.mcmp;


import java.util.HashMap;
import java.util.Map;


public class biome_playlists {
    public static Map<String, String[]> biomePlaylists = new HashMap<String, String[]>();

    public static final Map<String, String[]> defaultPlaylists = new HashMap<String, String[]>();

    public static void init() {
        defaultPlaylists.put("version", new String[]{"1"});
        defaultPlaylists.put("fallback", new String[]{"minecraft:music.game"});
        defaultPlaylists.put("menu", new String[]{"mcmp:menu"});
        defaultPlaylists.put("creative", new String[]{"minecraft:music.creative", "mcmp:menu"});

        // biomes
        String v = "minecraft:music.game"; // vanilla minecraft music
        // other forms of background music (biome specific vanilla songs) are not added to these playlists
        defaultPlaylists.put("minecraft:the_void", new String[]{v});
        defaultPlaylists.put("minecraft:plains", new String[]{"mcmp:plains", v});
        defaultPlaylists.put("minecraft:sunflower_plains", new String[]{"mcmp:plains", "mcmp:flower_forest", v});
        defaultPlaylists.put("minecraft:snowy_plains", new String[]{"mcmp:snow_plains", v});
        defaultPlaylists.put("minecraft:ice_spikes", new String[]{"mcmp:snow_plains", v});
        defaultPlaylists.put("minecraft:desert", new String[]{"mcmp:desert", v});
        defaultPlaylists.put("minecraft:swamp", new String[]{"mcmp:swamp", v});
        defaultPlaylists.put("minecraft:mangrove_swamp", new String[]{"mcmp:swamp", v});
        defaultPlaylists.put("minecraft:forest", new String[]{"mcmp:forest", v});
        defaultPlaylists.put("minecraft:flower_forest", new String[]{"mcmp:flower_forest", v});
        defaultPlaylists.put("minecraft:birch_forest", new String[]{"mcmp:birch_forest", v});
        defaultPlaylists.put("minecraft:dark_forest", new String[]{"mcmp:dark_forest", v});
        defaultPlaylists.put("minecraft:old_growth_birch_forest", new String[]{"mcmp:birch_forest", v});
        defaultPlaylists.put("minecraft:old_growth_pine_taiga", new String[]{"mcmp:taiga", v});
        defaultPlaylists.put("minecraft:old_growth_spruce_taiga", new String[]{"mcmp:taiga", v});
        defaultPlaylists.put("minecraft:taiga", new String[]{"mcmp:taiga", v});
        defaultPlaylists.put("minecraft:snowy_taiga", new String[]{"mcmp:snow_plains", "mcmp:taiga", v});
        defaultPlaylists.put("minecraft:savanna", new String[]{"mcmp:savanna", v});
        defaultPlaylists.put("minecraft:savanna_plateau", new String[]{"mcmp:savanna", v});
        defaultPlaylists.put("minecraft:windswept_hills", new String[]{"mcmp:windy", v});
        defaultPlaylists.put("minecraft:windswept_gravelly_hills", new String[]{"mcmp:windy", v});
        defaultPlaylists.put("minecraft:windswept_forest", new String[]{"mcmp:windy", "mcmp:forest", v});
        defaultPlaylists.put("minecraft:windswept_savanna", new String[]{"mcmp:windy", "mcmp:savanna", v});
        defaultPlaylists.put("minecraft:jungle", new String[]{"mcmp:jungle", v});
        defaultPlaylists.put("minecraft:sparse_jungle", new String[]{"mcmp:jungle", v});
        defaultPlaylists.put("minecraft:bamboo_jungle", new String[]{"mcmp:bamboo_jungle", v});
        defaultPlaylists.put("minecraft:badlands", new String[]{"mcmp:badlands", v});
        defaultPlaylists.put("minecraft:eroded_badlands", new String[]{"mcmp:badlands", v});
        defaultPlaylists.put("minecraft:wooded_badlands", new String[]{"mcmp:badlands", v});
        defaultPlaylists.put("minecraft:meadow", new String[]{"mcmp:meadow", v});
        defaultPlaylists.put("minecraft:cherry_grove", new String[]{"mcmp:cherry_grove", v});
        defaultPlaylists.put("minecraft:grove", new String[]{"mcmp:taiga", v});
        defaultPlaylists.put("minecraft:snowy_slopes", new String[]{"mcmp:mountains", v});
        defaultPlaylists.put("minecraft:frozen_peaks", new String[]{"mcmp:mountains", v});
        defaultPlaylists.put("minecraft:jagged_peaks", new String[]{"mcmp:mountains", v});
        defaultPlaylists.put("minecraft:stony_peaks", new String[]{"mcmp:mountains", v});
        defaultPlaylists.put("minecraft:river", new String[]{"mcmp:river", v});
        defaultPlaylists.put("minecraft:frozen_river", new String[]{"mcmp:river", v});
        defaultPlaylists.put("minecraft:beach", new String[]{"mcmp:warm_ocean", "mcmp:ocean", v});
        defaultPlaylists.put("minecraft:snowy_beach", new String[]{"mcmp:cold_ocean", "mcmp:ocean", v});
        defaultPlaylists.put("minecraft:stony_shore", new String[]{"mcmp:river", v});
        defaultPlaylists.put("minecraft:warm_ocean", new String[]{"mcmp:warm_ocean", "mcmp:ocean", v});
        defaultPlaylists.put("minecraft:lukewarm_ocean", new String[]{"mcmp:warm_ocean", "mcmp:ocean", v});
        defaultPlaylists.put("minecraft:deep_lukewarm_ocean", new String[]{"mcmp:deep_ocean", "mcmp:warm_ocean", "mcmp:ocean", v});
        defaultPlaylists.put("minecraft:ocean", new String[]{"mcmp:ocean", v});
        defaultPlaylists.put("minecraft:deep_ocean", new String[]{"mcmp:deep_ocean", "mcmp:ocean", v});
        defaultPlaylists.put("minecraft:cold_ocean", new String[]{"mcmp:ocean", "mcmp:cold_ocean", v});
        defaultPlaylists.put("minecraft:deep_cold_ocean", new String[]{"mcmp:deep_ocean", "mcmp:cold_ocean", "mcmp:ocean", v});
        defaultPlaylists.put("minecraft:frozen_ocean", new String[]{"mcmp:ocean", "mcmp:cold_ocean", v});
        defaultPlaylists.put("minecraft:deep_frozen_ocean", new String[]{"mcmp:deep_ocean", "mcmp:cold_ocean", "mcmp:ocean",v});
        defaultPlaylists.put("minecraft:mushroom_fields", new String[]{"mcmp:mushroom_island-01",});
        defaultPlaylists.put("minecraft:dripstone_caves", new String[]{"mcmp:dripstone_caves", v});
        defaultPlaylists.put("minecraft:lush_caves", new String[]{"mcmp:lush_caves", v});
        defaultPlaylists.put("minecraft:deep_dark", new String[]{"mcmp:deep_dark", v});

        defaultPlaylists.put("minecraft:nether_wastes", new String[]{"mcmp:nether_wastes", v});
        defaultPlaylists.put("minecraft:warped_forest", new String[]{v});
        defaultPlaylists.put("minecraft:crimson_forest", new String[]{"mcmp:crimson_forest", v});
        defaultPlaylists.put("minecraft:soul_sand_valley", new String[]{"mcmp:soul_sand_valley", v});
        defaultPlaylists.put("minecraft:basalt_deltas", new String[]{"mcmp:basalt_deltas", v});

        defaultPlaylists.put("minecraft:the_end", new String[]{"mcmp:end", v});
        defaultPlaylists.put("minecraft:end_highlands", new String[]{"mcmp:end", v});
        defaultPlaylists.put("minecraft:small_end_islands", new String[]{"mcmp:end", v});
        defaultPlaylists.put("minecraft:end_barrens", new String[]{"mcmp:end", v});

        MCMP_main.LOGGER.info("Default Playlist successfully initialized");




    }
}
