package net.fabricmc.mcmp;

import java.util.*;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.gui.screen.Screen;

import net.minecraft.sound.SoundEvent;


import net.fabricmc.api.ModInitializer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.fabricmc.mcmp.register_resources.*;
import net.fabricmc.mcmp.config_manager;

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
		LOGGER.info("Registering Resources");
		register_resources.register_resources();
		LOGGER.info("Resources Registered.");
		LOGGER.info("Loading Config Values");
		config_manager.init();

	}

	public static PositionedSoundInstance currentlyPlaying = null;

	public static MinecraftClient mc = MinecraftClient.getInstance();

	public static boolean inMainMenu() {
		return mc.player == null;
	}

	public static PositionedSoundInstance nowPlaying() {
		return currentlyPlaying;
	}
	
	// literally just the vanilla playSong function but it won't be called by vanilla minecraft...
	public static void playSong(SoundEvent songToPlay) {
        if (songToPlay != null) {
            if (currentlyPlaying != null)
                mc.getSoundManager().stop(currentlyPlaying);

            currentlyPlaying = PositionedSoundInstance.ambient(songToPlay);

            mc.getSoundManager().play(currentlyPlaying);
        }
	}

	public static SoundEvent pickSong() {
		if (!inMainMenu()) {
			String biome = mc.world.getBiome(mc.player.getBlockPos()).getKey().get().getValue().toString();
			switch(biome) {
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
}
