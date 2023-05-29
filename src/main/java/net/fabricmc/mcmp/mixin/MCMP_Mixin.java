package net.fabricmc.mcmp.mixin;

import net.fabricmc.mcmp.MCMP_main;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicTracker;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicTracker.class)
public class MCMP_Mixin {
	@Inject(method = "play()V", at = @At(value = "HEAD"), cancellable = true) // TODO: this will run every tick, a cleaner system is needed!!!!!
	private void playMixin(CallbackInfo info) {
		info.cancel();
		if (MCMP_main.currentlyPlaying == null) {
			MCMP_main.playSong(MCMP_main.MY_SOUND_EVENT, false, MinecraftClient.getInstance());
			MCMP_main.LOGGER.info("song played");
		}
	}
}
