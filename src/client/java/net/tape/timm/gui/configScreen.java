package net.tape.timm.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.tape.timm.configManager;
import net.tape.timm.gui.widget.configCheckbox;
import net.tape.timm.gui.widget.configSlider;
import net.tape.timm.modConfig;
import net.tape.timm.timmMain;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class configScreen extends Screen {
    public configScreen() {
        super(new TranslatableText("timm.config.text"));
    }
    int txtcol = 0xffffff;

    public configCheckbox debugLogs;

    configSlider menuMinSlider, menuMaxSlider, songMinSlider, songMaxSlider;

    @Override
    public void init() {


        menuMinSlider = new configSlider(width/2,30,150,20, "timm.config.menuMin.slider", (double) modConfig.minMenuDelay / modConfig.maxMenuDelay, modConfig.minMenuDelay, this::updateMenuMin);
        menuMaxSlider = new configSlider(width/2,50,150,20, "timm.config.menuMax.slider",(double) modConfig.maxMenuDelay / (36000-modConfig.minMenuDelay), modConfig.maxMenuDelay, this::updateMenuMax);
        songMinSlider = new configSlider(width/2,80,150,20, "timm.config.songMin.slider",(double) modConfig.minGameDelay / modConfig.maxGameDelay, modConfig.minGameDelay, this::updateGameMin);
        songMaxSlider = new configSlider(width/2,100,150,20, "timm.config.songMax.slider",(double) modConfig.maxGameDelay / (36000-modConfig.minGameDelay), modConfig.maxGameDelay, this::updateGameMax);




        debugLogs = new configCheckbox(width/2, 130, 20, 20, "debug", modConfig.debugLogging);
        addDrawableChild(debugLogs);

        addDrawableChild(new ButtonWidget(width-120-10 /* screen width - button width - 10 pixel offset */, height-20-10, 120, 20, new TranslatableText("timm.config.close.text"), button -> this.close()));
        // TODO: add a "load defaults" button

        addDrawableChild(menuMinSlider);
        addDrawableChild(menuMaxSlider);
        addDrawableChild(songMinSlider);
        addDrawableChild(songMaxSlider);

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);



        drawCenteredText(matrices, timmMain.mc.textRenderer, new TranslatableText("timm.config.menuMin.text"), 10, 30, txtcol);
        drawCenteredText(matrices, timmMain.mc.textRenderer, new TranslatableText("timm.config.menuMax.text"), 10, 50, txtcol);
        drawCenteredText(matrices, timmMain.mc.textRenderer, new TranslatableText("timm.config.songMin.text"), 10, 80, txtcol);
        drawCenteredText(matrices, timmMain.mc.textRenderer, new TranslatableText("timm.config.songMax.text"), 10, 100, txtcol);
        drawCenteredText(matrices, timmMain.mc.textRenderer, new TranslatableText("timm.config.debug.text"), 10, 130, txtcol);




    }

    private void updateDebug(boolean check) {
        modConfig.debugLogging = check;
        String checkString = String.valueOf(check);
        modConfig.configMap.replace("debug", new String[]{"bool", checkString});
        configManager.update_cfg();
    }

    public long lerp(long min, long max, double t) {
        if ((t > 1.0) || (t < 0.0)) {
            throw new ArithmeticException("lerp value out of bounds");
        } else {
            return Math.round(max*t + min*(1-t));
        }
    }

    private long updateMenuMin(double value) {
        long realVal = lerp(1, modConfig.maxMenuDelay, value);
        String valString = String.valueOf(realVal);
        modConfig.minMenuDelay = realVal;
        modConfig.configMap.replace("menuMinDelay", new String[]{"long", valString});
        configManager.update_cfg();

        return realVal;
    }

    private long updateMenuMax(double value) {
        long realVal = lerp(modConfig.minMenuDelay, 36000, value);
        String valString = String.valueOf(realVal);
        modConfig.maxMenuDelay = realVal;
        modConfig.configMap.replace("menuMaxDelay", new String[]{"long", valString});
        configManager.update_cfg();
        return realVal;
    }

    private long updateGameMin(double value) {
        long realVal = lerp(1, modConfig.maxGameDelay, value);
        String valString = String.valueOf(realVal);
        modConfig.minGameDelay = realVal;
        modConfig.configMap.replace("gameMinDelay", new String[]{"long", valString});
        configManager.update_cfg();
        return realVal;
    }

    private long updateGameMax(double value) {
        long realVal = lerp(modConfig.minGameDelay, 36000, value);
        String valString = String.valueOf(realVal);
        modConfig.maxGameDelay = realVal;
        modConfig.configMap.replace("gameMaxDelay", new String[]{"long", valString});
        configManager.update_cfg();
        return realVal;
    }

}
