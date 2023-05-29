package net.fabricmc.mcmp;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;


import net.minecraft.sound.SoundCategory;
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
		LOGGER.info("MC Music Project initialized.");
	}

	public static PositionedSoundInstance currentlyPlaying = null;

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





	public static final Identifier MY_SOUND_ID = new Identifier("mcmp:my_sound");
    public static SoundEvent MY_SOUND_EVENT = SoundEvent.of(MY_SOUND_ID);

}
