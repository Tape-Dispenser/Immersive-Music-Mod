package net.fabricmc.timm.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.fabricmc.timm.timm_main.LOGGER;
import static net.fabricmc.timm.timm_main.mc;

import net.fabricmc.timm.song_controls;

@Mixin(TitleScreen.class)
public class ExampleMixin {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		if (song_controls.currentlyPlaying == null) {
			song_controls.playSong(song_controls.pickSong());
			LOGGER.info("now playing: ".concat(song_controls.currentlyPlaying.getId().toString()));
		}
		if (!mc.getSoundManager().isPlaying(song_controls.currentlyPlaying)) {
			song_controls.playSong(song_controls.pickSong());
			LOGGER.info("now playing: ".concat(song_controls.currentlyPlaying.getId().toString()));
		}
	}
}
