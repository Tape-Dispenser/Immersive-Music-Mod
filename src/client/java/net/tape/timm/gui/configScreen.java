package net.tape.timm.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;
import net.tape.timm.configManager;
import net.tape.timm.gui.widget.SimpleButton;
import net.tape.timm.gui.widget.configSlider;
import net.tape.timm.modConfig;
import net.tape.timm.timmMain;
import net.tape.timm.util.math;

import static net.tape.timm.timmMain.mc;

@Environment(EnvType.CLIENT)
public class configScreen extends Screen {
    public configScreen(Screen parent) {
        super(Text.translatable("timm.config.text"));
        this.parent = parent;
    }

    int txtcol = 0xffffff;
    private final Screen parent;

    CheckboxWidget debugLogs, alwaysCheckUpdates, alwaysGetUpdates;
    configSlider menuMinSlider, menuMaxSlider, songMinSlider, songMaxSlider;



    @Override
    public void init() {

        menuMinSlider = new configSlider(width/2,30,150,20, "timm.config.menuMin.slider", (double) modConfig.minMenuDelay / modConfig.maxMenuDelay, modConfig.minMenuDelay, this::updateMenuMin);
        menuMaxSlider = new configSlider(width/2,50,150,20, "timm.config.menuMax.slider",(double) modConfig.maxMenuDelay / (36000-modConfig.minMenuDelay), modConfig.maxMenuDelay, this::updateMenuMax);
        songMinSlider = new configSlider(width/2,80,150,20, "timm.config.songMin.slider",(double) modConfig.minGameDelay / modConfig.maxGameDelay, modConfig.minGameDelay, this::updateGameMin);
        songMaxSlider = new configSlider(width/2,100,150,20, "timm.config.songMax.slider",(double) modConfig.maxGameDelay / (36000-modConfig.minGameDelay), modConfig.maxGameDelay, this::updateGameMax);

        debugLogs = CheckboxWidget.builder(Text.literal(""), mc.textRenderer)
                .checked(modConfig.debugLogging)
                .callback((checkbox, checked) -> updateDebug(checked))
                .build();
        debugLogs.setPosition(width/2, 130);

        addDrawableChild(debugLogs);

        addDrawableChild(new SimpleButton(
                this,
                button -> this.close(),
                "timm.config.close.text",
                "timm.config.close.tooltip",
                -10,
                -10
        ));

        addDrawableChild(new SimpleButton(
                this,
                button -> resetConfig(),
                "timm.config.reset.text",
                "timm.config.reset.tooltip",
                10,
                -10
        ));

        addDrawableChild(menuMinSlider);
        addDrawableChild(menuMaxSlider);
        addDrawableChild(songMinSlider);
        addDrawableChild(songMaxSlider);

    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(ctx);
        super.render(ctx, mouseX, mouseY, delta);

        ctx.drawText(mc.textRenderer, Text.translatable("timm.config.menuMin.text"), 10, 30, txtcol, false);
        ctx.drawText(mc.textRenderer, Text.translatable("timm.config.menuMax.text"), 10, 50, txtcol, false);
        ctx.drawText(mc.textRenderer, Text.translatable("timm.config.songMin.text"), 10, 80, txtcol, false);
        ctx.drawText(mc.textRenderer, Text.translatable("timm.config.songMax.text"), 10, 100, txtcol, false);
        ctx.drawText(mc.textRenderer, Text.translatable("timm.config.debug.text"), 10, 130, txtcol, false);

    }

    public void resetConfig() {
        modConfig.configMap = modConfig.defaultConfig;
        modConfig.copyVals();
        configManager.update_cfg(modConfig.configMap);

        menuMinSlider.updateWidget(modConfig.minMenuDelay);
        menuMaxSlider.updateWidget(modConfig.maxMenuDelay);
        songMaxSlider.updateWidget(modConfig.maxGameDelay);
        songMinSlider.updateWidget(modConfig.minGameDelay);
    }

    @Override
    public void close() {
        mc.setScreen(parent);
    }

    private void updateDebug(boolean check) {
        modConfig.debugLogging = check;
        String checkString = String.valueOf(check);
        modConfig.configMap.replace("debug", checkString);
        configManager.update_cfg(modConfig.configMap);
    }

    private long updateMenuMin(double value) {
        long realVal = math.lerp(1, modConfig.maxMenuDelay, value);
        String valString = String.valueOf(realVal);
        modConfig.minMenuDelay = realVal;
        modConfig.configMap.replace("menuMinDelay", valString);
        configManager.update_cfg(modConfig.configMap);

        return realVal;
    }

    private long updateMenuMax(double value) {
        long realVal = math.lerp(modConfig.minMenuDelay, 36000, value);
        String valString = String.valueOf(realVal);
        modConfig.maxMenuDelay = realVal;
        modConfig.configMap.replace("menuMaxDelay", valString);
        configManager.update_cfg(modConfig.configMap);
        return realVal;
    }

    private long updateGameMin(double value) {
        long realVal = math.lerp(1, modConfig.maxGameDelay, value);
        String valString = String.valueOf(realVal);
        modConfig.minGameDelay = realVal;
        modConfig.configMap.replace("gameMinDelay", valString);
        configManager.update_cfg(modConfig.configMap);
        return realVal;
    }

    private long updateGameMax(double value) {
        long realVal = math.lerp(modConfig.minGameDelay, 36000, value);
        String valString = String.valueOf(realVal);
        modConfig.maxGameDelay = realVal;
        modConfig.configMap.replace("gameMaxDelay", valString);
        configManager.update_cfg(modConfig.configMap);
        return realVal;
    }

}
