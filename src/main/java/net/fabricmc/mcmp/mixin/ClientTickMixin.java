package net.fabricmc.mcmp.mixin;

import net.fabricmc.mcmp.MCMP_main;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.fabricmc.mcmp.MCMP_main.*;


@Mixin(MinecraftClient.class)
public class ClientTickMixin {
	@Inject(at = @At("TAIL"), method = "tick()V")
	private void init(CallbackInfo info) {
		if (!mc.getSoundManager().isPlaying(currentlyPlaying)) {
			if (!inTimer) {
				// initialize timer
				timer = song_rng.nextInt(minDelay,maxDelay);
				inTimer = true;
			}
			timer -= 1;
			if (inTimer && timer == 0) {
				MCMP_main.playSong(MCMP_main.pickSong());
				inTimer = false;
				LOGGER.info("now playing: ".concat(currentlyPlaying.getId().toString()));
			}
			return;
		}
		if (MCMP_main.currentlyPlaying == null) {
			MCMP_main.playSong(MCMP_main.pickSong());
			LOGGER.info("now playing: ".concat(currentlyPlaying.getId().toString()));
		}
	}
}





