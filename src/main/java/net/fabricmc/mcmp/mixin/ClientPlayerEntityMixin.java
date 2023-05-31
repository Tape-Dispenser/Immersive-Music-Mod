package net.fabricmc.mcmp.mixin;

import net.fabricmc.mcmp.MCMP_main;

import net.minecraft.client.network.ClientPlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
	@Inject(at = @At("TAIL"), method = "init()V")
	private void init(CallbackInfo info) {
		MCMP_main.inGame = true;
		MCMP_main.LOGGER.info(" who cares");
	}
}



