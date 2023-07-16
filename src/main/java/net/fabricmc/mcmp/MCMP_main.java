package net.fabricmc.mcmp;

import java.util.*;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.gui.screen.Screen;

import net.minecraft.sound.SoundEvent;


import net.fabricmc.api.ModInitializer;


import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.fabricmc.mcmp.config_manager.biomes;
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

	public static Random song_rng = new Random();
	
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
		Integer index = 0;
		String biome = "menu";
		if (!inMainMenu()) {
			biome = mc.world.getBiome(mc.player.getBlockPos()).getKey().get().getValue().toString();
		}
		Vector<String> playlist = biomes.get(biome);											// get playlist
		if (playlist.size() == 0){																// check if empty
			playlist = biomes.get("fallback");
		}
		index = Math.abs(song_rng.nextInt() % playlist.size());									// pick random index inside playlist
		String song = playlist.get(index).replaceAll("\"", "").strip();		// get the song
		if (song.equals("vanilla")) {
			index = Math.abs(song_rng.nextInt() % playlist.size());
			try {
				song = biomes.get(song).get(index).replaceAll("\"", "").strip(); // have fun understanding this when i'm not on a violent amount of cannabis
			}
			catch (ArrayIndexOutOfBoundsException e) {
				MCMP_main.LOGGER.error("Make sure the fallback playlist (by default the vanilla playlist) isn't empty!");
				throw new RuntimeException(e);
			}
		}

		try {
			return SoundEvent.of(Identifier.tryParse(song));
		}
		catch (NullPointerException e) {
			return null;
		}

	}
}
