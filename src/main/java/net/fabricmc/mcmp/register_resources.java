package net.fabricmc.mcmp;

import net.minecraft.client.sound.Sound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.entry.RegistryEntry;

public class register_resources {
    public static void init() {
        //  iterate through all entries in biome_playlists.json and make a list of all unique songs
        Registry.register(Registries.SOUND_EVENT, register_resources.MY_SOUND_ID, SoundEvent.of(MY_SOUND_ID));
        Registry.register(Registries.SOUND_EVENT, register_resources.MENU, SoundEvent.of(MENU));
        Registry.register(Registries.SOUND_EVENT, register_resources.BADLANDS, SoundEvent.of(BADLANDS));
        Registry.register(Registries.SOUND_EVENT, register_resources.BAMBOO_JUNGLE, SoundEvent.of(BAMBOO_JUNGLE));
        Registry.register(Registries.SOUND_EVENT, register_resources.BIRCH_FOREST, SoundEvent.of(BIRCH_FOREST));
        Registry.register(Registries.SOUND_EVENT, register_resources.CHERRY_GROVE, SoundEvent.of(CHERRY_GROVE));
        Registry.register(Registries.SOUND_EVENT, register_resources.COLD_OCEAN, SoundEvent.of(COLD_OCEAN));
        Registry.register(Registries.SOUND_EVENT, register_resources.DARK_FOREST, SoundEvent.of(DARK_FOREST));
        Registry.register(Registries.SOUND_EVENT, register_resources.DEEP_DARK, SoundEvent.of(DEEP_DARK));
        Registry.register(Registries.SOUND_EVENT, register_resources.DESERT, SoundEvent.of(DESERT));
        Registry.register(Registries.SOUND_EVENT, register_resources.DRIPSTONE_CAVES, SoundEvent.of(DRIPSTONE_CAVES));
        Registry.register(Registries.SOUND_EVENT, register_resources.FLOWER_FOREST, SoundEvent.of(FLOWER_FOREST));
        Registry.register(Registries.SOUND_EVENT, register_resources.FOREST, SoundEvent.of(FOREST));
        Registry.register(Registries.SOUND_EVENT, register_resources.MOUNTAINS, SoundEvent.of(MOUNTAINS));
        Registry.register(Registries.SOUND_EVENT, register_resources.JUNGLE, SoundEvent.of(JUNGLE));
        Registry.register(Registries.SOUND_EVENT, register_resources.LUSH_CAVES, SoundEvent.of(LUSH_CAVES));
        Registry.register(Registries.SOUND_EVENT, register_resources.MEADOW, SoundEvent.of(MEADOW));
        Registry.register(Registries.SOUND_EVENT, register_resources.MUSHROOM, SoundEvent.of(MUSHROOM));
        Registry.register(Registries.SOUND_EVENT, register_resources.OCEAN, SoundEvent.of(OCEAN));
        Registry.register(Registries.SOUND_EVENT, register_resources.PLAINS, SoundEvent.of(PLAINS));
        Registry.register(Registries.SOUND_EVENT, register_resources.SNOW_PLAINS, SoundEvent.of(SNOW_PLAINS));
        Registry.register(Registries.SOUND_EVENT, register_resources.RIVER, SoundEvent.of(RIVER));
        Registry.register(Registries.SOUND_EVENT, register_resources.SWAMP, SoundEvent.of(SWAMP));
        Registry.register(Registries.SOUND_EVENT, register_resources.TAIGA, SoundEvent.of(TAIGA));
        Registry.register(Registries.SOUND_EVENT, register_resources.WARM_OCEAN, SoundEvent.of(WARM_OCEAN));
        Registry.register(Registries.SOUND_EVENT, register_resources.WINDY, SoundEvent.of(WINDY));

        Registry.register(Registries.SOUND_EVENT, register_resources.BASALT_DELTAS, SoundEvent.of(BASALT_DELTAS));
        Registry.register(Registries.SOUND_EVENT, register_resources.CRIMSON_FOREST, SoundEvent.of(CRIMSON_FOREST));
        Registry.register(Registries.SOUND_EVENT, register_resources.NETHER_WASTES, SoundEvent.of(NETHER_WASTES));
        Registry.register(Registries.SOUND_EVENT, register_resources.SOUL_SAND_VALLEY, SoundEvent.of(SOUL_SAND_VALLEY));

        Registry.register(Registries.SOUND_EVENT, register_resources.END, SoundEvent.of(END));
    }

    public static final Identifier MY_SOUND_ID = new Identifier("mcmp:my_sound");
    public static final Identifier MENU = new Identifier("mcmp:menu");
    public static final Identifier BADLANDS = new Identifier("mcmp:badlands");
    public static final Identifier BAMBOO_JUNGLE = new Identifier("mcmp:bamboo_jungle");
    public static final Identifier BIRCH_FOREST = new Identifier("mcmp:birch_forest");
    public static final Identifier CHERRY_GROVE = new Identifier("mcmp:cherry_grove");
    public static final Identifier COLD_OCEAN = new Identifier("mcmp:cold_ocean");
    public static final Identifier DARK_FOREST = new Identifier("mcmp:dark_forest");
    public static final Identifier DEEP_DARK = new Identifier("mcmp:deep_dark");
    public static final Identifier DESERT = new Identifier("mcmp:desert");
    public static final Identifier DRIPSTONE_CAVES = new Identifier("mcmp:dripstone_caves");
    public static final Identifier FLOWER_FOREST = new Identifier("mcmp:flower_forest");
    public static final Identifier FOREST = new Identifier("mcmp:forest");
    public static final Identifier MOUNTAINS = new Identifier("mcmp:mountains");
    public static final Identifier JUNGLE = new Identifier("mcmp:jungle");
    public static final Identifier LUSH_CAVES = new Identifier("mcmp:lush_caves");
    public static final Identifier MEADOW = new Identifier("mcmp:meadow");
    public static final Identifier MUSHROOM = new Identifier("mcmp:mushroom");
    public static final Identifier OCEAN = new Identifier("mcmp:ocean");
    public static final Identifier PLAINS = new Identifier("mcmp:plains");
    public static final Identifier SNOW_PLAINS = new Identifier("mcmp:snow_plains");
    public static final Identifier RIVER = new Identifier("mcmp:river");
    public static final Identifier SWAMP = new Identifier("mcmp:swamp");
    public static final Identifier TAIGA = new Identifier("mcmp:taiga");
    public static final Identifier WARM_OCEAN = new Identifier("mcmp:warm_ocean");
    public static final Identifier WINDY = new Identifier("mcmp:windy_hills");


    public static final Identifier BASALT_DELTAS = new Identifier("mcmp:basalt_deltas");
    public static final Identifier CRIMSON_FOREST = new Identifier("mcmp:crimson_forest");
    public static final Identifier NETHER_WASTES = new Identifier("mcmp:nether_wastes");
    public static final Identifier SOUL_SAND_VALLEY = new Identifier("mcmp:soul_sand_valley");



    public static final Identifier END = new Identifier("mcmp:end");

}
