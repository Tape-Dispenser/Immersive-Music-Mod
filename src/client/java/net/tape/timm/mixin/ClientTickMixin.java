package net.tape.timm.mixin;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.tape.timm.audio.Song;
import net.tape.timm.audio.SongRegistry;
import net.tape.timm.audio.SongSelector;
import net.tape.timm.modConfig;
import net.tape.timm.songControls;

import net.minecraft.client.MinecraftClient;

import net.tape.timm.timmMain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.tape.timm.timmMain.LOGGER;


@Mixin(MinecraftClient.class)
public class ClientTickMixin {


	@Inject(at = @At("TAIL"), method = "tick()V")
	private void init(CallbackInfo info) {

		if (!songControls.soundEngineStarted) {
			return;
		}

		if (!SongRegistry.isStarted()) {
			return;
		}

		if (SongRegistry.songList.isEmpty()) {
			return;
		}

		if (!songControls.inTimer) {
			if (songControls.nowPlaying() != null) {
				return;
			}
			// not in timer, and current song has run out
			// set timer and rng delay time
			songControls.inTimer = true;
			long x; // delay time
			if (songControls.lastSong == null) {
				x = 10;
			} else {
				//TODO: FIX THIS!!!
				x = songControls.pickDelay(modConfig.minMenuDelay, modConfig.maxMenuDelay, songControls.song_rng);
			}
			songControls.timer = x;

			// debug logging
			if (modConfig.debugLogging) {
				LOGGER.info("ticks until next song: ".concat(String.valueOf(x)));
			}
			return;
		}

		// in delay timer

		if (songControls.timer > 0) {
			songControls.timer -= 1;
			return;
		}
		// timer has run out, play new song
		songControls.inTimer = false;
		songControls.play(SongSelector.pickSong(songControls.song_rng));

		// debug logging
		if (modConfig.debugLogging) {
			Song x = songControls.nowPlaying();
			if (x != null) {
				LOGGER.info(String.format("now playing : %s (%s)", x.getSongName(), x.getPathOrId()));
			} else {
				LOGGER.warn("failed to pick song!");
			}
		}
	}
}






