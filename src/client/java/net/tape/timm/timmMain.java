package net.tape.timm;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;


import net.tape.timm.audio.AudioManager;
import net.tape.timm.audio.SongRegistry;
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


		LOGGER.info("Loading default config values...");
		modConfig.init();
		LOGGER.info("Loaded default config values.");


		LOGGER.info("Loading config values from file...");
		configManager.init();
		LOGGER.info("Config values loaded");


		LOGGER.info("Copying config values to memory...");
		modConfig.copyVals();
		LOGGER.info("Config values copied to memory.");


		LOGGER.info("Checking for song updates...");
		getSongs.update();
		LOGGER.info("Successfully updated any outdated songs.");


		LOGGER.info("Registering songs...");
		SongRegistry.init();
		LOGGER.info("Songs registered");


		LOGGER.info("Loading playlists to memory...");
		biomePlaylists.init();
		LOGGER.info("Playlists loaded.");











		LOGGER.info("Initializing Song Controls...");
		songControls.init();
		LOGGER.info("Song Controls Initialized.");



		LOGGER.info("Initializing Commands...");
		ClientCommandRegistrationCallback.EVENT.register(registerCommands::init);
		LOGGER.info("Commands Initialized.");





		LOGGER.info("Starting OpenAL...");
		AudioManager.init();


		LOGGER.info("TIMM successfully initialized.");

	}





	public static MinecraftClient mc = MinecraftClient.getInstance();

}
