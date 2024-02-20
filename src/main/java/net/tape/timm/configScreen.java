package net.tape.timm;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class configScreen extends Screen {
    public configScreen() {
        super(Text.translatable("timm.config.text"));
    }

    public ButtonWidget button1;
    public ButtonWidget close;

    @Override
    public void init() {
        button1 = ButtonWidget.builder(Text.translatable("timm.config.button1.text"), button -> button1Click())
                .dimensions(width/4, height/2, 200, 20)
                .tooltip(Tooltip.of(Text.translatable("timm.config.button1.tooltip")))
                .build();

        close = ButtonWidget.builder(Text.translatable("timm.config.close.text"), button -> button1Click())
                .dimensions(width/4 * 3, height/2, 200, 20)
                .tooltip(Tooltip.of(Text.translatable("timm.config.close.tooltip")))
                .build();

        addDrawableChild(button1);
        addDrawableChild(close);
    }





    static void button1Click() {
        timmMain.LOGGER.info(Text.translatable("timm.config.button1.click").toString());
    }

    void closeClick() {
        close();
        timmMain.LOGGER.info(Text.translatable("timm.config.close.click").toString());
    }
}
