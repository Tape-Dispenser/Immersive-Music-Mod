package net.fabricmc.mcmp.mixin;

import net.fabricmc.mcmp.MCMP_main;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.fabricmc.mcmp.MCMP_main.LOGGER;
import static net.fabricmc.mcmp.MCMP_main.currentlyPlaying;

@Mixin(TitleScreen.class)
public class ExampleMixin {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		MCMP_main.playSong(MCMP_main.pickSong());
		LOGGER.info("now playing: ".concat(currentlyPlaying.getId().toString()));
		// turns out the example mixin works great for playing menu music
		// who'da thought
	}
}
