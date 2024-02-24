package net.tape.timm.gui.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.tape.timm.timmMain;

public class minSlider extends SliderWidget {
    public minSlider(int x, int y, int w, int h, Text text, int val) {
        super(x, y, w, h, text, val);
    }

    @Override
    protected void updateMessage() {

    }

    @Override
    public void onRelease(double mouseX, double mouseY) {
        super.playDownSound(MinecraftClient.getInstance().getSoundManager());
        timmMain.LOGGER.info(String.valueOf(this.value));
    }

    @Override
    protected void applyValue() {
        // TODO: translate slider value (0.0 - 1.0) to tick value (0 - max)
        // TODO: figure out a clean way to pass max value to this function, maybe a pointer to the variable????

    }
}
