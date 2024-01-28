package net.fabricmc.timm.mixin;

import static net.fabricmc.timm.timm_main.LOGGER;
import net.minecraft.client.sound.MusicTracker;
import net.fabricmc.timm.song_controls;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicTracker.class)
public class MusicSoundMixin {
	@Inject(method = "play(Lnet/minecraft/sound/MusicSound;)V", at = @At(value = "HEAD"), cancellable = true) // TODO: this will run every tick, a cleaner system is needed!!!!!
	private void playMixin(CallbackInfo info) {
		info.cancel();
	}
	

	@Inject(method = "stop()V", at = @At(value = "HEAD"))
	private void stopMixin(CallbackInfo info) {
		song_controls.currentlyPlaying = null;
		LOGGER.info("song stopped");
	}
}
