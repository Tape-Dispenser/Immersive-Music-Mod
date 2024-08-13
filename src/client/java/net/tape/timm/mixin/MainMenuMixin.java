package net.tape.timm.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.tape.timm.gui.UpdatePromptScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
public abstract class MainMenuMixin {
    @Shadow public abstract void setScreen(@Nullable Screen screen);

    @Redirect(method = "collectLoadTimes(Lnet/minecraft/client/MinecraftClient$LoadingContext;)V", at = @At(
            value = "INVOKE", target = "net/minecraft/client/MinecraftClient.onInitFinished (Lnet/minecraft/client/MinecraftClient$LoadingContext;)Ljava/lang/Runnable;")
    )
    private Runnable init(MinecraftClient instance, MinecraftClient.LoadingContext loadingContext) {
        Runnable runnable;
        runnable = () -> {
            TitleScreen titleScreen = new TitleScreen(true);
            UpdatePromptScreen updateScreen = new UpdatePromptScreen(titleScreen);
            this.setScreen(updateScreen);
        };
        return runnable;
    }
}