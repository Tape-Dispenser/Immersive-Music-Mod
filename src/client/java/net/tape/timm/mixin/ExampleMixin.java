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
			LOGGER.info("now playing: ".concat(songControls.nowPlaying())); // this gives a warning but because a song is picked it shouldn't give a null song ever (unless playlist is null maybe)
		}
	}
}
