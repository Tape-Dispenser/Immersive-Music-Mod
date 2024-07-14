package net.tape.timm;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.impl.resource.loader.ModNioResourcePack;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.MinecraftClient;


import net.minecraft.resource.ResourceType;
import net.tape.timm.audio.AudioManager;
import net.tape.timm.audio.SongRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;


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

		LOGGER.info("Loading playlists to memory...");
		biomePlaylists.init();
		LOGGER.info("Playlists loaded.");


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


		LOGGER.info("Initializing Song Controls...");
		songControls.init();
		LOGGER.info("Song Controls Initialized.");



		LOGGER.info("Initializing Commands...");
		ClientCommandRegistrationCallback.EVENT.register(registerCommands::init);
		LOGGER.info("Commands Initialized.");





		LOGGER.info("Starting OpenAL...");
		AudioManager.init();


		LOGGER.info("TIMM successfully initialized.");

		LOGGER.info("Testing resource loading path...");
		/*for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
			if (!mod.getMetadata().getId().equals("minecraft")) continue;
			for (Path path : mod.get()) {
				LOGGER.info(path.toAbsolutePath().toString());
				LOGGER.info(ResourceType.CLIENT_RESOURCES.getDirectory());
			}
		}*/
		LOGGER.info("Testing done");

	}





	public static MinecraftClient mc = MinecraftClient.getInstance();

}
