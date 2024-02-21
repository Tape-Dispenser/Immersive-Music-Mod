package net.tape.timm.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.tape.timm.gui.widget.closeButton;
import net.tape.timm.timmMain;

@Environment(EnvType.CLIENT)
public class configScreen extends Screen {
    public configScreen() {
        super(Text.translatable("timm.config.text"));
    }



    @Override
    public void init() {
        addDrawableChild(new closeButton(this));
    }







    void closeClick() {
        close();
        timmMain.LOGGER.info(Text.translatable("timm.config.close.click").getString());
    }
}
