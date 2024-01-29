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
		if (songControls.currentlyPlaying == null) {
			songControls.playSong(songControls.pickSong());
			LOGGER.info("now playing: ".concat(songControls.currentlyPlaying.getId().toString()));
		}
		if (!mc.getSoundManager().isPlaying(songControls.currentlyPlaying)) {
			songControls.playSong(songControls.pickSong());
			LOGGER.info("now playing: ".concat(songControls.currentlyPlaying.getId().toString()));
		}
	}
}
