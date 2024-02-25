package net.tape.timm.mixin;

import net.tape.timm.songControls;

import net.minecraft.client.MinecraftClient;

import net.tape.timm.timmMain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MinecraftClient.class)
public class ClientTickMixin {


	@Inject(at = @At("TAIL"), method = "tick()V")
	private void init(CallbackInfo info) {
		if (songControls.nowPlaying() == null) {
			// TODO: check if game is paused
			songControls.timer -= 1;
			if (songControls.inTimer && songControls.timer == 0) {
				songControls.play(songControls.pickSong());
				songControls.inTimer = false;
				timmMain.LOGGER.info("now playing: ".concat(songControls.lastSong.getId().toString()));
			}
			return;
		}
	}
}





