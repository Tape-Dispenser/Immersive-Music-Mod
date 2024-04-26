package net.tape.timm;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;


import net.fabricmc.api.ClientModInitializer;


import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundSystem;
import net.tape.timm.access.SoundManagerAccess;
import net.tape.timm.access.SoundSystemAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class timmMain implements ClientModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("TIMM");

	@Override
	public void onInitializeClient() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.


		LOGGER.info("Registering Resources...");
		registerResources.init();
		LOGGER.info("Resources Registered");



		LOGGER.info("Initializing Playlists...");
		biomePlaylists.init();
		LOGGER.info("Playlists Initialized.");



		LOGGER.info("Loading Default Config Values...");
		modConfig.init();
		LOGGER.info("Loaded Default Config Values.");



		LOGGER.info("Loading Config Values...");
		configManager.init();
		LOGGER.info("Config Values Loaded");



		LOGGER.info("Copying config values to memory...");
		modConfig.copyVals();
		LOGGER.info("Config values copied to memory.");



		LOGGER.info("Initializing Song Controls...");
		songControls.init();
		LOGGER.info("Song Controls Initialized.");



		LOGGER.info("Initializing Commands...");
		ClientCommandRegistrationCallback.EVENT.register(registerCommands::init);
		LOGGER.info("Commands Initialized.");


		LOGGER.info("Checking Server for new songs...");
		getSongs.getNewSongs();


		LOGGER.info("TIMM successfully initialized.");

	}





	public static MinecraftClient mc = MinecraftClient.getInstance();

}
