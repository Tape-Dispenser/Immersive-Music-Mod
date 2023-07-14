package net.fabricmc.mcmp;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.entry.RegistryEntry;

public class register_resources {
    public static void register_resources() {
        //  iterate through all entries in biome_playlists.json and make a list of all unique songs
        Registry.register(Registries.SOUND_EVENT, register_resources.MY_SOUND_ID, MY_SOUND_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.BASALT_DELTAS_1, BASALT_DELTAS_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.BIRCH_FOREST_1, BIRCH_FOREST_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.BIRCH_FOREST_2, BIRCH_FOREST_2_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.CHERRY_FOREST_1, CHERRY_FOREST_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.DARK_OAK_FOREST_1, DARK_OAK_FOREST_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.DEEP_DARK_1, DEEP_DARK_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.DESERT_1, DESERT_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.FOREST_1, FOREST_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.JUNGLE_1, JUNGLE_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.LUSH_CAVES_1, LUSH_CAVES_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.LUSH_CAVES_2, LUSH_CAVES_2_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.MENU_1, MENU_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.MENU_2, MENU_2_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.MOUNTAIN_1, MOUNTAIN_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.MOUNTAINS_MEADOWS_1, MOUNTAINS_MEADOWS_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.MUSHROOM_ISLAND_1, MUSHROOM_ISLAND_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.NETHER_WASTES_1, NETHER_WASTES_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.PLAINS_1, PLAINS_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.SNOW_PLAINS_1, SNOW_PLAINS_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.SOUL_SAND_VALLEY_1, SOUL_SAND_VALLEY_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.TAIGA_1, TAIGA_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.TAIGA_2, TAIGA_2_EVENT);
        Registry.register(Registries.SOUND_EVENT, register_resources.WARM_OCEAN_1, WARM_OCEAN_1_EVENT);
        return;
    }

    public static final Identifier MY_SOUND_ID = new Identifier("mcmp:my_sound");
    public static SoundEvent MY_SOUND_EVENT = SoundEvent.of(MY_SOUND_ID);


    public static final Identifier BASALT_DELTAS_1 = new Identifier("mcmp:basalt_deltas-01");
    public static SoundEvent BASALT_DELTAS_1_EVENT = SoundEvent.of(BASALT_DELTAS_1);

    public static final Identifier BIRCH_FOREST_1 = new Identifier("mcmp:birch_forest-01");
    public static SoundEvent BIRCH_FOREST_1_EVENT = SoundEvent.of(BIRCH_FOREST_1);

    public static final Identifier BIRCH_FOREST_2 = new Identifier("mcmp:birch_forest-02");
    public static SoundEvent BIRCH_FOREST_2_EVENT = SoundEvent.of(BIRCH_FOREST_2);

    public static final Identifier CHERRY_FOREST_1 = new Identifier("mcmp:cherry_forest-01");
    public static SoundEvent CHERRY_FOREST_1_EVENT = SoundEvent.of(CHERRY_FOREST_1);

    public static final Identifier DARK_OAK_FOREST_1 = new Identifier("mcmp:dark_oak_forest-01");
    public static SoundEvent DARK_OAK_FOREST_1_EVENT = SoundEvent.of(DARK_OAK_FOREST_1);

    public static final Identifier DEEP_DARK_1 = new Identifier("mcmp:deep_dark-01");
    public static SoundEvent DEEP_DARK_1_EVENT = SoundEvent.of(DEEP_DARK_1);

    public static final Identifier DESERT_1 = new Identifier("mcmp:desert-01");
    public static SoundEvent DESERT_1_EVENT = SoundEvent.of(DESERT_1);

    public static final Identifier FOREST_1 = new Identifier("mcmp:forest-01");
    public static SoundEvent FOREST_1_EVENT = SoundEvent.of(FOREST_1);

    public static final Identifier JUNGLE_1 = new Identifier("mcmp:jungle-01");
    public static SoundEvent JUNGLE_1_EVENT = SoundEvent.of(JUNGLE_1);

    public static final Identifier LUSH_CAVES_1 = new Identifier("mcmp:lush_caves-01");
    public static SoundEvent LUSH_CAVES_1_EVENT = SoundEvent.of(LUSH_CAVES_1);

    public static final Identifier LUSH_CAVES_2 = new Identifier("mcmp:lush_caves-02");
    public static SoundEvent LUSH_CAVES_2_EVENT = SoundEvent.of(LUSH_CAVES_2);

    public static final Identifier MENU_1 = new Identifier("mcmp:menu-01");
    public static SoundEvent MENU_1_EVENT = SoundEvent.of(MENU_1);

    public static final Identifier MENU_2 = new Identifier("mcmp:menu-02");
    public static SoundEvent MENU_2_EVENT = SoundEvent.of(MENU_2);

    public static final Identifier MOUNTAIN_1 = new Identifier("mcmp:mountain-01");
    public static SoundEvent MOUNTAIN_1_EVENT = SoundEvent.of(MOUNTAIN_1);

    public static final Identifier MOUNTAINS_MEADOWS_1 = new Identifier("mcmp:mountains_meadows-01");
    public static SoundEvent MOUNTAINS_MEADOWS_1_EVENT = SoundEvent.of(MOUNTAINS_MEADOWS_1);

    public static final Identifier MUSHROOM_ISLAND_1 = new Identifier("mcmp:mushroom_island-01");
    public static SoundEvent MUSHROOM_ISLAND_1_EVENT = SoundEvent.of(MUSHROOM_ISLAND_1);

    public static final Identifier NETHER_WASTES_1 = new Identifier("mcmp:nether_wastes-01");
    public static SoundEvent NETHER_WASTES_1_EVENT = SoundEvent.of(NETHER_WASTES_1);

    public static final Identifier PLAINS_1 = new Identifier("mcmp:plains-01");
    public static SoundEvent PLAINS_1_EVENT = SoundEvent.of(PLAINS_1);

    public static final Identifier SNOW_PLAINS_1 = new Identifier("mcmp:snow_plains-01");
    public static SoundEvent SNOW_PLAINS_1_EVENT = SoundEvent.of(SNOW_PLAINS_1);

    public static final Identifier SOUL_SAND_VALLEY_1 = new Identifier("mcmp:soul_sand_valley-01");
    public static SoundEvent SOUL_SAND_VALLEY_1_EVENT = SoundEvent.of(SOUL_SAND_VALLEY_1);

    public static final Identifier TAIGA_1 = new Identifier("mcmp:taiga-01");
    public static SoundEvent TAIGA_1_EVENT = SoundEvent.of(TAIGA_1);

    public static final Identifier TAIGA_2 = new Identifier("mcmp:taiga-02");
    public static SoundEvent TAIGA_2_EVENT = SoundEvent.of(TAIGA_2);

    public static final Identifier WARM_OCEAN_1 = new Identifier("mcmp:warm_ocean-01");
    public static SoundEvent WARM_OCEAN_1_EVENT = SoundEvent.of(WARM_OCEAN_1);


}
