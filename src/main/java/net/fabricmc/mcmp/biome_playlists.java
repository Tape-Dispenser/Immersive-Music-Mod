package net.fabricmc.mcmp;


import java.util.HashMap;
import java.util.Map;


public class biome_playlists {
    public static Map<String, String[]> biomePlaylists = new HashMap<String, String[]>();

    public static final Map<String, String[]> defaultPlaylists = new HashMap<String, String[]>();

    public static void init() {
        defaultPlaylists.put("version", new String[]{"0"});
        defaultPlaylists.put("fallback", new String[]{"minecraft:music.game"});
        defaultPlaylists.put("menu", new String[]{"mcmp:menu-01", "mcmp:menu-02"});
        defaultPlaylists.put("creative", new String[]{"minecraft:music.creative"});

        // biomes
        String v = "minecraft:music.game"; // vanilla minecraft music
        // other forms of background music (biome specific vanilla songs) are not added to these playlists
        defaultPlaylists.put("minecraft:the_void", new String[]{v});
        defaultPlaylists.put("minecraft:plains", new String[]{"mcmp:plains-01", "mcmp:plains-02", v});
        defaultPlaylists.put("minecraft:sunflower_plains", new String[]{v});
        defaultPlaylists.put("minecraft:snowy_plains", new String[]{"mcmp:snow_plains-01", "mcmp:snow_plains-02", v});
        defaultPlaylists.put("minecraft:ice_spikes", new String[]{v});
        defaultPlaylists.put("minecraft:desert", new String[]{"mcmp:desert-01", v});
        defaultPlaylists.put("minecraft:swamp", new String[]{"mcmp:swamp-01", v});
        defaultPlaylists.put("minecraft:mangrove_swamp", new String[]{v});
        defaultPlaylists.put("minecraft:forest", new String[]{"mcmp:forest-01", v});
        defaultPlaylists.put("minecraft:flower_forest", new String[]{v});
        defaultPlaylists.put("minecraft:birch_forest", new String[]{"mcmp:birch_forest-01", "mcmp:birch_forest-02", v});
        defaultPlaylists.put("minecraft:dark_forest", new String[]{"mcmp:dark_oak_forest-01", v});
        defaultPlaylists.put("minecraft:old_growth_birch_forest", new String[]{"mcmp:birch_forest-01", "mcmp:birch_forest-02", v});
        defaultPlaylists.put("minecraft:old_growth_pine_taiga", new String[]{"mcmp:taiga-01", "mcmp:taiga-02", v});
        defaultPlaylists.put("minecraft:old_growth_spruce_taiga", new String[]{"mcmp:taiga-01", "mcmp:taiga-02", v});
        defaultPlaylists.put("minecraft:taiga", new String[]{"mcmp:taiga-01", "mcmp:taiga-02", v});
        defaultPlaylists.put("minecraft:snowy_taiga", new String[]{v});
        defaultPlaylists.put("minecraft:savanna", new String[]{v});
        defaultPlaylists.put("minecraft:savanna_plateau", new String[]{v});
        defaultPlaylists.put("minecraft:windswept_hills", new String[]{v});
        defaultPlaylists.put("minecraft:windswept_gravelly_hills", new String[]{v});
        defaultPlaylists.put("minecraft:windswept_forest", new String[]{v});
        defaultPlaylists.put("minecraft:windswept_savanna", new String[]{v});
        defaultPlaylists.put("minecraft:jungle", new String[]{"mcmp:jungle-01", v});
        defaultPlaylists.put("minecraft:sparse_jungle", new String[]{"mcmp:jungle-01", v});
        defaultPlaylists.put("minecraft:bamboo_jungle", new String[]{"mcmp:bamboo_jungle-01", v});
        defaultPlaylists.put("minecraft:badlands", new String[]{v});
        defaultPlaylists.put("minecraft:eroded_badlands", new String[]{v});
        defaultPlaylists.put("minecraft:wooded_badlands", new String[]{v});
        defaultPlaylists.put("minecraft:meadow", new String[]{"mcmp:mountains_meadows-01", v});
        defaultPlaylists.put("minecraft:cherry_grove", new String[]{"mcmp:cherry_forest-01", v});
        defaultPlaylists.put("minecraft:grove", new String[]{v});
        defaultPlaylists.put("minecraft:snowy_slopes", new String[]{"mcmp:mountain-01", v});
        defaultPlaylists.put("minecraft:frozen_peaks", new String[]{"mcmp:mountain-01", v});
        defaultPlaylists.put("minecraft:jagged_peaks", new String[]{"mcmp:mountain-01", v});
        defaultPlaylists.put("minecraft:stony_peaks", new String[]{"mcmp:mountain-01", v});
        defaultPlaylists.put("minecraft:river", new String[]{v});
        defaultPlaylists.put("minecraft:frozen_river", new String[]{v});
        defaultPlaylists.put("minecraft:beach", new String[]{v});
        defaultPlaylists.put("minecraft:snowy_beach", new String[]{v});
        defaultPlaylists.put("minecraft:stony_shore", new String[]{v});
        defaultPlaylists.put("minecraft:warm_ocean", new String[]{"mcmp:warm_ocean-01", v});
        defaultPlaylists.put("minecraft:lukewarm_ocean", new String[]{v});
        defaultPlaylists.put("minecraft:deep_lukewarm_ocean", new String[]{v});
        defaultPlaylists.put("minecraft:ocean", new String[]{v});
        defaultPlaylists.put("minecraft:deep_ocean", new String[]{"mcmp:deep_ocean-01", v});
        defaultPlaylists.put("minecraft:cold_ocean", new String[]{v});
        defaultPlaylists.put("minecraft:deep_cold_ocean", new String[]{v});
        defaultPlaylists.put("minecraft:frozen_ocean", new String[]{v});
        defaultPlaylists.put("minecraft:deep_frozen_ocean", new String[]{v});
        defaultPlaylists.put("minecraft:mushroom_fields", new String[]{"mcmp:mushroom_island-01",});
        defaultPlaylists.put("minecraft:dripstone_caves", new String[]{v});
        defaultPlaylists.put("minecraft:lush_caves", new String[]{"mcmp:lush_caves-01", "mcmp:lush_caves-02", v});
        defaultPlaylists.put("minecraft:deep_dark", new String[]{"mcmp:deep_dark-01", "mcmp:deep_dark-02", v});
        defaultPlaylists.put("minecraft:nether_wastes", new String[]{"mcmp:nether_wastes-01", v});
        defaultPlaylists.put("minecraft:warped_forest", new String[]{v});
        defaultPlaylists.put("minecraft:crimson_forest", new String[]{"mcmp:crimson_forest-01", v});
        defaultPlaylists.put("minecraft:soul_sand_valley", new String[]{"mcmp:soul_sand_valley-01", v});
        defaultPlaylists.put("minecraft:basalt_deltas", new String[]{"mcmp:basalt_deltas-01", v});
        defaultPlaylists.put("minecraft:the_end", new String[]{v});
        defaultPlaylists.put("minecraft:end_highlands", new String[]{v});
        defaultPlaylists.put("minecraft:small_end_islands", new String[]{v});
        defaultPlaylists.put("minecraft:end_barrens", new String[]{v});

        MCMP_main.LOGGER.info("Default Playlist successfully initialized");




    }
}
