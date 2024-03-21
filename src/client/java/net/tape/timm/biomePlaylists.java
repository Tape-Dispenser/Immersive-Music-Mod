package net.tape.timm;


import java.util.HashMap;
import java.util.Map;

import static net.tape.timm.timmMain.LOGGER;


public class biomePlaylists {
    public static Map<String, String[]> biomePlaylists = new HashMap<String, String[]>();

    public static final Map<String, String[]> defaultPlaylists = new HashMap<String, String[]>();

    public static void init() {
        defaultPlaylists.put("version", new String[]{"1"});
        defaultPlaylists.put("fallback", new String[]{"minecraft:music.game"});
        defaultPlaylists.put("menu", new String[]{"timm:menu"});
        defaultPlaylists.put("creative", new String[]{"minecraft:music.creative", "timm:menu"});

        // biomes
        String v = "minecraft:music.game"; // vanilla minecraft music
        // other forms of background music (biome specific vanilla songs) are not added to these playlists
        defaultPlaylists.put("minecraft:the_void", new String[]{v});
        defaultPlaylists.put("minecraft:plains", new String[]{"timm:plains", v});
        defaultPlaylists.put("minecraft:sunflower_plains", new String[]{"timm:plains", "timm:flower_forest", v});
        defaultPlaylists.put("minecraft:snowy_plains", new String[]{"timm:snow_plains", v});
        defaultPlaylists.put("minecraft:ice_spikes", new String[]{"timm:snow_plains", v});
        defaultPlaylists.put("minecraft:desert", new String[]{"timm:desert", v});
        defaultPlaylists.put("minecraft:swamp", new String[]{"timm:swamp", v});
        defaultPlaylists.put("minecraft:mangrove_swamp", new String[]{"timm:swamp", v});
        defaultPlaylists.put("minecraft:forest", new String[]{"timm:forest", v});
        defaultPlaylists.put("minecraft:flower_forest", new String[]{"timm:flower_forest", v});
        defaultPlaylists.put("minecraft:birch_forest", new String[]{"timm:birch_forest", v});
        defaultPlaylists.put("minecraft:dark_forest", new String[]{"timm:dark_forest", v});
        defaultPlaylists.put("minecraft:old_growth_birch_forest", new String[]{"timm:birch_forest", v});
        defaultPlaylists.put("minecraft:old_growth_pine_taiga", new String[]{"timm:taiga", v});
        defaultPlaylists.put("minecraft:old_growth_spruce_taiga", new String[]{"timm:taiga", v});
        defaultPlaylists.put("minecraft:taiga", new String[]{"timm:taiga", v});
        defaultPlaylists.put("minecraft:snowy_taiga", new String[]{"timm:snow_plains", "timm:taiga", v});
        defaultPlaylists.put("minecraft:savanna", new String[]{"timm:savanna", v});
        defaultPlaylists.put("minecraft:savanna_plateau", new String[]{"timm:savanna", v});
        defaultPlaylists.put("minecraft:windswept_hills", new String[]{"timm:windy", v});
        defaultPlaylists.put("minecraft:windswept_gravelly_hills", new String[]{"timm:windy", v});
        defaultPlaylists.put("minecraft:windswept_forest", new String[]{"timm:windy", "timm:forest", v});
        defaultPlaylists.put("minecraft:windswept_savanna", new String[]{"timm:windy", "timm:savanna", v});
        defaultPlaylists.put("minecraft:jungle", new String[]{"timm:jungle", v});
        defaultPlaylists.put("minecraft:sparse_jungle", new String[]{"timm:jungle", v});
        defaultPlaylists.put("minecraft:bamboo_jungle", new String[]{"timm:bamboo_jungle", v});
        defaultPlaylists.put("minecraft:badlands", new String[]{"timm:badlands", v});
        defaultPlaylists.put("minecraft:eroded_badlands", new String[]{"timm:badlands", v});
        defaultPlaylists.put("minecraft:wooded_badlands", new String[]{"timm:badlands", v});
        defaultPlaylists.put("minecraft:meadow", new String[]{"timm:meadow", v});
        defaultPlaylists.put("minecraft:cherry_grove", new String[]{"timm:cherry_grove", v});
        defaultPlaylists.put("minecraft:grove", new String[]{"timm:taiga", v});
        defaultPlaylists.put("minecraft:snowy_slopes", new String[]{"timm:mountains", v});
        defaultPlaylists.put("minecraft:frozen_peaks", new String[]{"timm:mountains", v});
        defaultPlaylists.put("minecraft:jagged_peaks", new String[]{"timm:mountains", v});
        defaultPlaylists.put("minecraft:stony_peaks", new String[]{"timm:mountains", v});
        defaultPlaylists.put("minecraft:river", new String[]{"timm:river", v});
        defaultPlaylists.put("minecraft:frozen_river", new String[]{"timm:river", v});
        defaultPlaylists.put("minecraft:beach", new String[]{"timm:warm_ocean", "timm:ocean", v});
        defaultPlaylists.put("minecraft:snowy_beach", new String[]{"timm:cold_ocean", "timm:ocean", v});
        defaultPlaylists.put("minecraft:stony_shore", new String[]{"timm:river", v});
        defaultPlaylists.put("minecraft:warm_ocean", new String[]{"timm:warm_ocean", "timm:ocean", v});
        defaultPlaylists.put("minecraft:lukewarm_ocean", new String[]{"timm:warm_ocean", "timm:ocean", v});
        defaultPlaylists.put("minecraft:deep_lukewarm_ocean", new String[]{"timm:deep_ocean", "timm:warm_ocean", "timm:ocean", v});
        defaultPlaylists.put("minecraft:ocean", new String[]{"timm:ocean", v});
        defaultPlaylists.put("minecraft:deep_ocean", new String[]{"timm:deep_ocean", "timm:ocean", v});
        defaultPlaylists.put("minecraft:cold_ocean", new String[]{"timm:ocean", "timm:cold_ocean", v});
        defaultPlaylists.put("minecraft:deep_cold_ocean", new String[]{"timm:deep_ocean", "timm:cold_ocean", "timm:ocean", v});
        defaultPlaylists.put("minecraft:frozen_ocean", new String[]{"timm:ocean", "timm:cold_ocean", v});
        defaultPlaylists.put("minecraft:deep_frozen_ocean", new String[]{"timm:deep_ocean", "timm:cold_ocean", "timm:ocean",v});
        defaultPlaylists.put("minecraft:mushroom_fields", new String[]{"timm:mushroom_island-01",});
        defaultPlaylists.put("minecraft:dripstone_caves", new String[]{"timm:dripstone_caves", v});
        defaultPlaylists.put("minecraft:lush_caves", new String[]{"timm:lush_caves", v});
        defaultPlaylists.put("minecraft:deep_dark", new String[]{"timm:deep_dark", v});

        defaultPlaylists.put("minecraft:nether_wastes", new String[]{"timm:nether_wastes", v});
        defaultPlaylists.put("minecraft:warped_forest", new String[]{v});
        defaultPlaylists.put("minecraft:crimson_forest", new String[]{"timm:crimson_forest", v});
        defaultPlaylists.put("minecraft:soul_sand_valley", new String[]{"timm:soul_sand_valley", v});
        defaultPlaylists.put("minecraft:basalt_deltas", new String[]{"timm:basalt_deltas", v});

        defaultPlaylists.put("minecraft:the_end", new String[]{"timm:end", v});
        defaultPlaylists.put("minecraft:end_highlands", new String[]{"timm:end", v});
        defaultPlaylists.put("minecraft:small_end_islands", new String[]{"timm:end", v});
        defaultPlaylists.put("minecraft:end_barrens", new String[]{"timm:end", v});

        LOGGER.info("Default Playlist successfully initialized");




    }
}
