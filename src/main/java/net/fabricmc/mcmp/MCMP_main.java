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
		LOGGER.info("Registering Resources...");
		register_resources.init();
		LOGGER.info("Resources Registered");
		LOGGER.info("Initializing Playlists...");
		biome_playlists.init();
		LOGGER.info("Playlists Initialized.");
		LOGGER.info("Loading Config Values...");
		config_manager.init();
		LOGGER.info("Config Values Loaded");
		LOGGER.info("Initializing Song Controls...");
		song_controls.init();
		LOGGER.info("Song Controls Initialized");


		LOGGER.info("TIMM successfully initialized.");

	}



	public static MinecraftClient mc = MinecraftClient.getInstance();

	public static boolean inMainMenu() {
		return mc.player == null;
	}




	




}
