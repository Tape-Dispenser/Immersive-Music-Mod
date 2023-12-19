package net.fabricmc.mcmp.mixin;

import net.fabricmc.mcmp.MCMP_main;
import net.fabricmc.mcmp.song_controls;

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
		if (!mc.getSoundManager().isPlaying(song_controls.currentlyPlaying)) {
			// timer initialization moved to song_controls.pickSong()
			song_controls.timer -= 1;
			if (song_controls.inTimer && song_controls.timer == 0) {
				song_controls.playSong(song_controls.pickSong());
				song_controls.inTimer = false;
				LOGGER.info("now playing: ".concat(song_controls.currentlyPlaying.getId().toString()));
			}
			return;
		}
	}
}





