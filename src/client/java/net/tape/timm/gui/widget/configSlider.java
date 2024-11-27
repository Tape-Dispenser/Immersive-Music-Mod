package net.tape.timm.gui.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.tape.timm.modConfig;
import net.tape.timm.timmMain;
import net.tape.timm.util.math;

public class configSlider extends SliderWidget {
    public configSlider(int x, int y, int w, int h, String key, double val, String real, ReleaseAction callback, MakeReadable callback1) {
        super(x, y, w, h, Text.stringifiedTranslatable(key, real), val);
        this.releaseLambda = callback;
        this.readableLambda = callback1;
        this.translationKey = key;

    }

    final protected ReleaseAction releaseLambda;
    final protected MakeReadable readableLambda;
    private long ticks;
    final private String translationKey;

    public void updateWidget(long ticks) {
        // this is called after a config reset to visually update sliders and other widgets
        this.value = math.invLerp(1, 36000, ticks);
        this.applyValue();
        this.updateMessage();
    }

    @Override
    protected void updateMessage() {
        String readableValue = this.readableLambda.makeReadable(this.ticks);
        this.setMessage(Text.stringifiedTranslatable(translationKey, readableValue));
    }

    @Override
    public void onRelease(double mouseX, double mouseY) {
        super.playDownSound(MinecraftClient.getInstance().getSoundManager());

        if (modConfig.debugLogging) {
            String[] temp = this.translationKey.split("\\.");
            temp[temp.length - 1] = "text";
            String temp1 = String.join(".", temp);
            String message = temp1.concat(" = ").concat(String.valueOf(this.ticks));
            timmMain.LOGGER.info(message);
        }
    }

    @Override
    protected void applyValue() {
        // value translation is done in configScreen
        this.ticks = this.releaseLambda.onRelease(this.value);
    }

    @Environment(value= EnvType.CLIENT)
    public interface ReleaseAction {
        long onRelease(double value);
    }

    @Environment(value = EnvType.CLIENT)
    public interface MakeReadable {
        String makeReadable(long value);
    }
}
