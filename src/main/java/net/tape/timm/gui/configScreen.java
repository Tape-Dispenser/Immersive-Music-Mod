package net.tape.timm.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;
import net.tape.timm.configManager;
import net.tape.timm.gui.widget.closeButton;
import net.tape.timm.gui.widget.minSlider;
import net.tape.timm.modConfig;
import net.tape.timm.timmMain;

@Environment(EnvType.CLIENT)
public class configScreen extends Screen {
    public configScreen() {
        super(Text.translatable("timm.config.text"));
    }

    int txtcol = 0xffffff;

    public CheckboxWidget debugLogs;

    @Override
    public void init() {

        minSlider menuMin = new minSlider(0,0,100,20, Text.literal("foo"),0);

        addDrawableChild(new closeButton(this));
        addDrawableChild(menuMin);

        debugLogs = CheckboxWidget.builder(Text.translatable("timm.config.debugLogs.text"), timmMain.mc.textRenderer)
                .checked(modConfig.debugLogging)
                .callback((checkbox, checked) -> debugClick(checked))
                .build();
        debugLogs.setPosition(width - 10 - debugLogs.getWidth(), 130);
        addDrawableChild(debugLogs);

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

    private void debugClick(boolean check) {
        modConfig.debugLogging = check;
        modConfig.configMap.replace("debug", new String[]{"bool", String.valueOf(check)});
        configManager.update_cfg("debug logging", String.valueOf(check));

    }







    void closeClick() {
        close();
        timmMain.LOGGER.info(Text.translatable("timm.config.close.click").getString());
    }
}
