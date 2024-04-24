package net.tape.timm;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

public class registerResources {
    public static void init() {
        //  iterate through all entries in biome_playlists.json and make a list of all unique songs
        Registry.register(Registries.SOUND_EVENT, registerResources.MY_SOUND_ID, SoundEvent.of(MY_SOUND_ID));
        Registry.register(Registries.SOUND_EVENT, registerResources.MENU, SoundEvent.of(MENU));
        Registry.register(Registries.SOUND_EVENT, registerResources.BADLANDS, SoundEvent.of(BADLANDS));
        Registry.register(Registries.SOUND_EVENT, registerResources.BAMBOO_JUNGLE, SoundEvent.of(BAMBOO_JUNGLE));
        Registry.register(Registries.SOUND_EVENT, registerResources.BIRCH_FOREST, SoundEvent.of(BIRCH_FOREST));
        Registry.register(Registries.SOUND_EVENT, registerResources.CHERRY_GROVE, SoundEvent.of(CHERRY_GROVE));
        Registry.register(Registries.SOUND_EVENT, registerResources.COLD_OCEAN, SoundEvent.of(COLD_OCEAN));
        Registry.register(Registries.SOUND_EVENT, registerResources.DARK_FOREST, SoundEvent.of(DARK_FOREST));
        Registry.register(Registries.SOUND_EVENT, registerResources.DEEP_DARK, SoundEvent.of(DEEP_DARK));
        Registry.register(Registries.SOUND_EVENT, registerResources.DESERT, SoundEvent.of(DESERT));
        Registry.register(Registries.SOUND_EVENT, registerResources.DRIPSTONE_CAVES, SoundEvent.of(DRIPSTONE_CAVES));
        Registry.register(Registries.SOUND_EVENT, registerResources.FLOWER_FOREST, SoundEvent.of(FLOWER_FOREST));
        Registry.register(Registries.SOUND_EVENT, registerResources.FOREST, SoundEvent.of(FOREST));
        Registry.register(Registries.SOUND_EVENT, registerResources.MOUNTAINS, SoundEvent.of(MOUNTAINS));
        Registry.register(Registries.SOUND_EVENT, registerResources.JUNGLE, SoundEvent.of(JUNGLE));
        Registry.register(Registries.SOUND_EVENT, registerResources.LUSH_CAVES, SoundEvent.of(LUSH_CAVES));
        Registry.register(Registries.SOUND_EVENT, registerResources.MEADOW, SoundEvent.of(MEADOW));
        Registry.register(Registries.SOUND_EVENT, registerResources.MUSHROOM, SoundEvent.of(MUSHROOM));
        Registry.register(Registries.SOUND_EVENT, registerResources.OCEAN, SoundEvent.of(OCEAN));
        Registry.register(Registries.SOUND_EVENT, registerResources.PLAINS, SoundEvent.of(PLAINS));
        Registry.register(Registries.SOUND_EVENT, registerResources.SNOW_PLAINS, SoundEvent.of(SNOW_PLAINS));
        Registry.register(Registries.SOUND_EVENT, registerResources.RIVER, SoundEvent.of(RIVER));
        Registry.register(Registries.SOUND_EVENT, registerResources.SWAMP, SoundEvent.of(SWAMP));
        Registry.register(Registries.SOUND_EVENT, registerResources.TAIGA, SoundEvent.of(TAIGA));
        Registry.register(Registries.SOUND_EVENT, registerResources.WARM_OCEAN, SoundEvent.of(WARM_OCEAN));
        Registry.register(Registries.SOUND_EVENT, registerResources.WINDY, SoundEvent.of(WINDY));

        Registry.register(Registries.SOUND_EVENT, registerResources.BASALT_DELTAS, SoundEvent.of(BASALT_DELTAS));
        Registry.register(Registries.SOUND_EVENT, registerResources.CRIMSON_FOREST, SoundEvent.of(CRIMSON_FOREST));
        Registry.register(Registries.SOUND_EVENT, registerResources.NETHER_WASTES, SoundEvent.of(NETHER_WASTES));
        Registry.register(Registries.SOUND_EVENT, registerResources.SOUL_SAND_VALLEY, SoundEvent.of(SOUL_SAND_VALLEY));

        Registry.register(Registries.SOUND_EVENT, registerResources.END, SoundEvent.of(END));
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
