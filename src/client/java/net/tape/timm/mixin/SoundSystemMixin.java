package net.tape.timm.mixin;

import net.minecraft.client.sound.*;

import net.tape.timm.songControls;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(SoundSystem.class)


public class SoundSystemMixin {

	@Inject(method = "start()V",
			at = @At(value = "INVOKE", target = "org/slf4j/Logger.info (Lorg/slf4j/Marker;Ljava/lang/String;)V", ordinal = 0),
			slice = @Slice(
					from = @At(value = "CONSTANT", args = "stringValue=Sound engine started")
			)
	)
	private void soundStarted(CallbackInfo info) {
		songControls.soundEngineStarted = true;
	}

}

