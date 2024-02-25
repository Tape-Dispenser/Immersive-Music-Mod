package net.tape.timm.gui.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class configSlider extends SliderWidget {
    public configSlider(int x, int y, int w, int h, Text text, int val, ReleaseAction callback) {
        super(x, y, w, h, text, val);
        this.lambda = callback;

    }

    protected ReleaseAction lambda;
    private long realVal;

    @Override
    protected void updateMessage() {
        this.setMessage(Text.translatable("timm.config.menuMin.slider", this.realVal));
    }

    @Override
    public void onRelease(double mouseX, double mouseY) {
        super.playDownSound(MinecraftClient.getInstance().getSoundManager());
    }

    @Override
    protected void applyValue() {
        // value translation is done in configScreen
        this.realVal = this.lambda.onRelease(this.value);
    }

    @Environment(value= EnvType.CLIENT)
    public static interface ReleaseAction {
        public long onRelease(double value);
    }
}
