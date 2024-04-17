package net.tape.timm;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;


import net.fabricmc.api.ClientModInitializer;


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


		//LOGGER.info("Registering Resources...");
		//registerResources.init();
		//LOGGER.info("Resources Registered");

		LOGGER.info("who needs to register resources anyways XD");


		//LOGGER.info("Initializing Playlists...");
		//biomePlaylists.init();
		//LOGGER.info("Playlists Initialized.");

		LOGGER.info("don't really need playlists if you don't have songs to put in them...");


		//LOGGER.info("Loading Default Config Values...");
		//modConfig.init();
		//LOGGER.info("Loaded Default Config Values.");

		LOGGER.info("config values seem kinda bloat to me ngl");


		//LOGGER.info("Loading Config Values...");
		//configManager.init();
		//LOGGER.info("Config Values Loaded");

		LOGGER.info("bro this dumbass really needs two separate functions just to load config values smh");


		//LOGGER.info("Copying config values to memory...");
		//modConfig.copyVals();
		//LOGGER.info("Config values copied to memory.");

		LOGGER.info("we aint copying shit to memory bitch");


		//LOGGER.info("Initializing Song Controls...");
		//songControls.init();
		//LOGGER.info("Song Controls Initialized.");

		LOGGER.info("bro why would you have to initialize song controls they're literally just control functions");


		LOGGER.info("Initializing Commands...");
		ClientCommandRegistrationCallback.EVENT.register(registerCommands::init);
		LOGGER.info("Commands Initialized.");



		LOGGER.info("TIMM successfully initialized.");

	}
}
