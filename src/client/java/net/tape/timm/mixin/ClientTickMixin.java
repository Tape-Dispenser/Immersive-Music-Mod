package net.tape.timm.mixin;

import net.tape.timm.modConfig;
import net.tape.timm.songControls;
import net.tape.timm.util.Song;

import net.minecraft.client.MinecraftClient;

import net.tape.timm.timmMain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

import static net.tape.timm.timmMain.LOGGER;


@Mixin(MinecraftClient.class)
public class ClientTickMixin {


	@Inject(at = @At("TAIL"), method = "tick()V")
	private void init(CallbackInfo info) {
		if (songControls.soundEngineStarted) {

			if (songControls.inTimer) {

				if (songControls.timer == 0) {

					// timer has run out, play new song
					songControls.inTimer = false;
					songControls.play(songControls.pickSong());

					// debug logging
					if (modConfig.debugLogging) {
						String x = songControls.nowPlaying();
						if (x != null) {
							LOGGER.info("now playing : ".concat(x));
						} else {
							LOGGER.warn("failed to pick song!");
						}
					}

				} else {
					// decrement timer
					songControls.timer -= 1;
				}

			} else if (!timmMain.mc.getSoundManager().isPlaying(songControls.lastSoundInstance)) { // not in timer, and current song has run out
				// set timer and rng delay time
				songControls.inTimer = true;
				long x; // delay time
				if (songControls.lastSong == null) {
					x = 10;
				} else {
					if (songControls.lastSong.playlist != null) {
						if (Objects.equals(songControls.lastSong.playlist, "menu")) {
							x = songControls.pickDelay(modConfig.minMenuDelay, modConfig.maxMenuDelay, songControls.song_rng);
						} else {
							x = songControls.pickDelay(modConfig.minGameDelay, modConfig.maxGameDelay, songControls.song_rng);
						}
					} else {
						x = 10;
					}
				}
				songControls.timer = x;

				// debug logging
				if (modConfig.debugLogging) {
					LOGGER.info("ticks until next song: ".concat(String.valueOf(x)));
				}
			}
		}
	}
}





