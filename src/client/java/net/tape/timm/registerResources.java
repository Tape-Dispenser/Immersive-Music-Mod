package net.tape.timm;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class registerResources {
    public static void init() {
        //  iterate through all entries in biome_playlists.json and make a list of all unique songs
        Registry.register(Registry.SOUND_EVENT, registerResources.MY_SOUND_ID, new SoundEvent(MY_SOUND_ID));
        Registry.register(Registry.SOUND_EVENT, registerResources.MENU, new SoundEvent(MENU));
        Registry.register(Registry.SOUND_EVENT, registerResources.BADLANDS, new SoundEvent(BADLANDS));
        Registry.register(Registry.SOUND_EVENT, registerResources.BAMBOO_JUNGLE, new SoundEvent(BAMBOO_JUNGLE));
        Registry.register(Registry.SOUND_EVENT, registerResources.BIRCH_FOREST, new SoundEvent(BIRCH_FOREST));
        Registry.register(Registry.SOUND_EVENT, registerResources.CHERRY_GROVE, new SoundEvent(CHERRY_GROVE));
        Registry.register(Registry.SOUND_EVENT, registerResources.COLD_OCEAN, new SoundEvent(COLD_OCEAN));
        Registry.register(Registry.SOUND_EVENT, registerResources.DARK_FOREST, new SoundEvent(DARK_FOREST));
        Registry.register(Registry.SOUND_EVENT, registerResources.DEEP_DARK, new SoundEvent(DEEP_DARK));
        Registry.register(Registry.SOUND_EVENT, registerResources.DESERT, new SoundEvent(DESERT));
        Registry.register(Registry.SOUND_EVENT, registerResources.DRIPSTONE_CAVES, new SoundEvent(DRIPSTONE_CAVES));
        Registry.register(Registry.SOUND_EVENT, registerResources.FLOWER_FOREST, new SoundEvent(FLOWER_FOREST));
        Registry.register(Registry.SOUND_EVENT, registerResources.FOREST, new SoundEvent(FOREST));
        Registry.register(Registry.SOUND_EVENT, registerResources.MOUNTAINS, new SoundEvent(MOUNTAINS));
        Registry.register(Registry.SOUND_EVENT, registerResources.JUNGLE, new SoundEvent(JUNGLE));
        Registry.register(Registry.SOUND_EVENT, registerResources.LUSH_CAVES, new SoundEvent(LUSH_CAVES));
        Registry.register(Registry.SOUND_EVENT, registerResources.MEADOW, new SoundEvent(MEADOW));
        Registry.register(Registry.SOUND_EVENT, registerResources.MUSHROOM, new SoundEvent(MUSHROOM));
        Registry.register(Registry.SOUND_EVENT, registerResources.OCEAN, new SoundEvent(OCEAN));
        Registry.register(Registry.SOUND_EVENT, registerResources.PLAINS, new SoundEvent(PLAINS));
        Registry.register(Registry.SOUND_EVENT, registerResources.SNOW_PLAINS, new SoundEvent(SNOW_PLAINS));
        Registry.register(Registry.SOUND_EVENT, registerResources.RIVER, new SoundEvent(RIVER));
        Registry.register(Registry.SOUND_EVENT, registerResources.SWAMP, new SoundEvent(SWAMP));
        Registry.register(Registry.SOUND_EVENT, registerResources.TAIGA, new SoundEvent(TAIGA));
        Registry.register(Registry.SOUND_EVENT, registerResources.WARM_OCEAN, new SoundEvent(WARM_OCEAN));
        Registry.register(Registry.SOUND_EVENT, registerResources.WINDY, new SoundEvent(WINDY));

        Registry.register(Registry.SOUND_EVENT, registerResources.BASALT_DELTAS, new SoundEvent(BASALT_DELTAS));
        Registry.register(Registry.SOUND_EVENT, registerResources.CRIMSON_FOREST, new SoundEvent(CRIMSON_FOREST));
        Registry.register(Registry.SOUND_EVENT, registerResources.NETHER_WASTES, new SoundEvent(NETHER_WASTES));
        Registry.register(Registry.SOUND_EVENT, registerResources.SOUL_SAND_VALLEY, new SoundEvent(SOUL_SAND_VALLEY));

        Registry.register(Registry.SOUND_EVENT, registerResources.END, new SoundEvent(END));
    }

    public static final Identifier MY_SOUND_ID = new Identifier("timm:my_sound");
    public static final Identifier MENU = new Identifier("timm:menu");
    public static final Identifier BADLANDS = new Identifier("timm:badlands");
    public static final Identifier BAMBOO_JUNGLE = new Identifier("timm:bamboo_jungle");
    public static final Identifier BIRCH_FOREST = new Identifier("timm:birch_forest");
    public static final Identifier CHERRY_GROVE = new Identifier("timm:cherry_grove");
    public static final Identifier COLD_OCEAN = new Identifier("timm:cold_ocean");
    public static final Identifier DARK_FOREST = new Identifier("timm:dark_forest");
    public static final Identifier DEEP_DARK = new Identifier("timm:deep_dark");
    public static final Identifier DESERT = new Identifier("timm:desert");
    public static final Identifier DRIPSTONE_CAVES = new Identifier("timm:dripstone_caves");
    public static final Identifier FLOWER_FOREST = new Identifier("timm:flower_forest");
    public static final Identifier FOREST = new Identifier("timm:forest");
    public static final Identifier MOUNTAINS = new Identifier("timm:mountains");
    public static final Identifier JUNGLE = new Identifier("timm:jungle");
    public static final Identifier LUSH_CAVES = new Identifier("timm:lush_caves");
    public static final Identifier MEADOW = new Identifier("timm:meadow");
    public static final Identifier MUSHROOM = new Identifier("timm:mushroom");
    public static final Identifier OCEAN = new Identifier("timm:ocean");
    public static final Identifier PLAINS = new Identifier("timm:plains");
    public static final Identifier SNOW_PLAINS = new Identifier("timm:snow_plains");
    public static final Identifier RIVER = new Identifier("timm:river");
    public static final Identifier SWAMP = new Identifier("timm:swamp");
    public static final Identifier TAIGA = new Identifier("timm:taiga");
    public static final Identifier WARM_OCEAN = new Identifier("timm:warm_ocean");
    public static final Identifier WINDY = new Identifier("timm:windy_hills");


    public static final Identifier BASALT_DELTAS = new Identifier("timm:basalt_deltas");
    public static final Identifier CRIMSON_FOREST = new Identifier("timm:crimson_forest");
    public static final Identifier NETHER_WASTES = new Identifier("timm:nether_wastes");
    public static final Identifier SOUL_SAND_VALLEY = new Identifier("timm:soul_sand_valley");



    public static final Identifier END = new Identifier("timm:end");

}
