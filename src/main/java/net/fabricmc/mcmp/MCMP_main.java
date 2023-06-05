package net.fabricmc.mcmp;

import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.entry.RegistryEntry;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.gui.screen.Screen;

import net.minecraft.sound.SoundEvent;


import net.fabricmc.api.ModInitializer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MCMP_main implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("mcmp");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		Registry.register(Registries.SOUND_EVENT, MCMP_main.MY_SOUND_ID, MY_SOUND_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.BASALT_DELTAS_1, BASALT_DELTAS_1_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.BIRCH_FOREST_1, BIRCH_FOREST_1_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.BIRCH_FOREST_2, BIRCH_FOREST_2_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.CHERRY_FOREST_1, CHERRY_FOREST_1_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.DARK_OAK_FOREST_1, DARK_OAK_FOREST_1_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.DEEP_DARK_1, DEEP_DARK_1_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.DESERT_1, DESERT_1_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.FOREST_1, FOREST_1_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.JUNGLE_1, JUNGLE_1_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.LUSH_CAVES_1, LUSH_CAVES_1_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.LUSH_CAVES_2, LUSH_CAVES_2_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.MENU_1, MENU_1_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.MENU_2, MENU_2_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.MOUNTAIN_1, MOUNTAIN_1_EVENT);
		//Registry.register(Registries.SOUND_EVENT, MCMP_main.MOUNTAINS_MEADOWS_1, MOUNTAINS_MEADOWS_1_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.MUSHROOM_ISLAND_1, MUSHROOM_ISLAND_1_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.NETHER_WASTES_1, NETHER_WASTES_1_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.PLAINS_1, PLAINS_1_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.SNOW_PLAINS_1, SNOW_PLAINS_1_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.SOUL_SAND_VALLEY_1, SOUL_SAND_VALLEY_1_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.TAIGA_1, TAIGA_1_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.TAIGA_2, TAIGA_2_EVENT);
		Registry.register(Registries.SOUND_EVENT, MCMP_main.WARM_OCEAN_1, WARM_OCEAN_1_EVENT);

		
		LOGGER.info("MC Music Project initialized.");
	}

	public static PositionedSoundInstance currentlyPlaying = null;

	public static MinecraftClient mc = MinecraftClient.getInstance();

	public static boolean inMainMenu() {
		if (MinecraftClient.getInstance().currentScreen == null) {
			return false;
		}
		return true;
	}

	public static PositionedSoundInstance nowPlaying() {
		return currentlyPlaying;
	}
	
	// literally just the vanilla playSong function but it won't be called by vanilla minecraft...
	public static void playSong(SoundEvent songToPlay, boolean loop, MinecraftClient client) {
        if (songToPlay != null) {
            if (currentlyPlaying != null)
                client.getSoundManager().stop(currentlyPlaying);

            currentlyPlaying = PositionedSoundInstance.ambient(songToPlay);

            client.getSoundManager().play(currentlyPlaying);
        }
	}

	public static SoundEvent pickSong(MinecraftClient client) {
		if (!inMainMenu()) {
			RegistryEntry<Biome> biome = client.player.world.getBiome(client.player.getBlockPos());
			switch(biome.getKey().get().getValue().toString()) {
				case "minecraft:basalt_deltas":
					return SoundEvent.of(BASALT_DELTAS_1);
				case "minecraft:birch_forest":
					return SoundEvent.of(BIRCH_FOREST_1);
				case "minecraft:cherry_forest":
					return SoundEvent.of(CHERRY_FOREST_1);
				case "minecraft:dark_oak_forest":
					return SoundEvent.of(DARK_OAK_FOREST_1);
				case "minecraft:deep_dark":
					return SoundEvent.of(DEEP_DARK_1);
				case "minecraft:desert":
					return SoundEvent.of(DESERT_1);
				case "minecraft:forest":
					return SoundEvent.of(FOREST_1);
				case "minecraft:jungle":
					return SoundEvent.of(JUNGLE_1);
				case "minecraft:lush_caves":
					return SoundEvent.of(LUSH_CAVES_1);
				case "minecraft:mountain":
					return SoundEvent.of(MOUNTAIN_1);
				//case "minecraft:mountains_meadows":
				//	return SoundEvent.of(MOUNTAINS_MEADOWS_1);
				case "minecraft:mushroom_island":
					return SoundEvent.of(MUSHROOM_ISLAND_1);
				case "minecraft:nether_wastes":
					return SoundEvent.of(NETHER_WASTES_1);
				case "minecraft:plains":
					return SoundEvent.of(PLAINS_1);
				case "minecraft:snow_plains":
					return SoundEvent.of(SNOW_PLAINS_1);
				case "minecraft:soul_sand_valley":
					return SoundEvent.of(SOUL_SAND_VALLEY_1);
				case "minecraft:taiga":
					return SoundEvent.of(TAIGA_1);	
					case "minecraft:warm_ocean":
					return SoundEvent.of(WARM_OCEAN_1);
				default:
					return SoundEvent.of(MY_SOUND_ID);
			}
		} else {
			// menu music
			return SoundEvent.of(MENU_1);
		}
	}



	// surely there's a better way to do this i just have to find it first...

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

	//public static final Identifier MOUNTAINS_MEADOWS_1 = new Identifier("mcmp:mountains_meadows-01");
    //public static SoundEvent MOUNTAINS_MEADOWS_1_EVENT = SoundEvent.of(MOUNTAINS_MEADOWS_1);

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
