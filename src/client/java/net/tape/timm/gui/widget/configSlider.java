package net.tape.timm.gui.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.tape.timm.modConfig;
import net.tape.timm.timmMain;

public class configSlider extends SliderWidget {
    public configSlider(int x, int y, int w, int h, String key, double val, long real, ReleaseAction callback) {
        super(x, y, w, h, new TranslatableText(key, real), val);
        this.lambda = callback;
        this.translationKey = key;

    }

    final protected ReleaseAction lambda;
    private long realVal;
    final private String translationKey;

    @Override
    protected void updateMessage() {
        this.setMessage(new TranslatableText(translationKey, this.realVal));
    }

    @Override
    public void onRelease(double mouseX, double mouseY) {
        super.playDownSound(MinecraftClient.getInstance().getSoundManager());

        if (modConfig.debugLogging) {
            String[] temp = this.translationKey.split("\\.");
            temp[temp.length - 1] = "text";
            String temp1 = String.join(".", temp);
            String message = temp1.concat(" = ").concat(String.valueOf(this.realVal));
            timmMain.LOGGER.info(message);
        }
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
