package net.fabricmc.mcmp.mixin;

import net.fabricmc.mcmp.MCMP_main;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MinecraftClient.class)
public class ClientTickMixin {
	@Inject(at = @At("TAIL"), method = "tick()V")
	private void init(CallbackInfo info) {
		SoundEvent x = MCMP_main.pickSong(MCMP_main.mc);
		if (x != null) {
			if (MCMP_main.currentlyPlaying == null) {
				MCMP_main.playSong(x, false, MinecraftClient.getInstance());
			} else  {
				if (!MinecraftClient.getInstance().getSoundManager().isPlaying(MCMP_main.currentlyPlaying)) {
					MCMP_main.playSong(x, false, MinecraftClient.getInstance());
				}
			}
		}
	}
}




