package net.tape.timm.mixin;

import net.minecraft.client.sound.MusicTracker;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.tape.timm.songControls;

import net.tape.timm.timmMain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MusicTracker.class)
public class MusicSoundMixin {
	@Inject(method = "play(Lnet/minecraft/sound/MusicSound;)V", at = @At(value = "HEAD"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private void playMixin(MusicSound sound, CallbackInfo info) {
		SoundEvent se = sound.getSound().value();
		songControls.play(se);
		info.cancel();
	}
	

	@Inject(method = "stop()V", at = @At(value = "HEAD"))
	private void stopMixin(CallbackInfo info) {
		songControls.stop();
		songControls.lastSong = null;
		timmMain.LOGGER.info("song stopped");
	}

	@Inject(method = "tick()V", at = @At(value = "HEAD"), cancellable = true)
	private void tickMixin(CallbackInfo info) {
		info.cancel();
	}
}
