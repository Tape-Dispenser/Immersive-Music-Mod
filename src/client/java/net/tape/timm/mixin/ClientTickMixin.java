package net.tape.timm.mixin;

import net.tape.timm.songControls;

import net.minecraft.client.MinecraftClient;

import net.tape.timm.timmMain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.tape.timm.timmMain.LOGGER;


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

				String x = songControls.nowPlaying();
				if (x != null) {
					LOGGER.info("now playing : ".concat(x));
				} else {
					LOGGER.warn("failed to pick song!");
				}
			}
		}
	}
}





