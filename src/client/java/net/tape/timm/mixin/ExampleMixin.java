package net.tape.timm.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import net.tape.timm.songControls;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.tape.timm.timmMain.LOGGER;
import static net.tape.timm.timmMain.mc;

@Mixin(TitleScreen.class)
public class ExampleMixin {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		String x = songControls.nowPlaying();
		if (x == null) {
			songControls.play(songControls.pickSong());
		}
		x = songControls.nowPlaying();
		if (x != null) {
			LOGGER.info("now playing : ".concat(x));
		} else {
			LOGGER.warn("failed to pick song!");
		}

	}
}
