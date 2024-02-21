package net.tape.timm.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
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

    int txtcol = 0xffffff;

    @Override
    public void init() {
        addDrawableChild(new closeButton(this));

    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        super.render(ctx, mouseX, mouseY, delta);



        ctx.drawText(timmMain.mc.textRenderer, Text.translatable("timm.config.songMin.text"), 10, 30, txtcol, false);
        ctx.drawText(timmMain.mc.textRenderer, Text.translatable("timm.config.songMax.text"), 10, 50, txtcol, false);
        ctx.drawText(timmMain.mc.textRenderer, Text.translatable("timm.config.menuMin.text"), 10, 80, txtcol, false);
        ctx.drawText(timmMain.mc.textRenderer, Text.translatable("timm.config.menuMax.text"), 10, 100, txtcol, false);
        ctx.drawText(timmMain.mc.textRenderer, Text.translatable("timm.config.debug.text"), 10, 130, txtcol, false);



    }







    void closeClick() {
        close();
        timmMain.LOGGER.info(Text.translatable("timm.config.close.click").getString());
    }
}
