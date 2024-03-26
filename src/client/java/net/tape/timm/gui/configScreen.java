package net.tape.timm.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;
import net.tape.timm.configManager;
import net.tape.timm.gui.widget.closeButton;
import net.tape.timm.gui.widget.configSlider;
import net.tape.timm.modConfig;
import net.tape.timm.timmMain;

@Environment(EnvType.CLIENT)
public class configScreen extends Screen {
    public configScreen() {
        super(Text.translatable("timm.config.text"));
    }

    int txtcol = 0xffffff;

    public CheckboxWidget debugLogs;

    configSlider menuMinSlider, menuMaxSlider, songMinSlider, songMaxSlider;

    @Override
    public void init() {


        menuMinSlider = new configSlider(width/2,30,150,20, "timm.config.menuMin.slider", (double) modConfig.minMenuDelay / modConfig.maxMenuDelay, modConfig.minMenuDelay, this::updateMenuMin);
        menuMaxSlider = new configSlider(width/2,50,150,20, "timm.config.menuMax.slider",(double) modConfig.maxMenuDelay / (36000-modConfig.minMenuDelay), modConfig.maxMenuDelay, this::updateMenuMax);
        songMinSlider = new configSlider(width/2,80,150,20, "timm.config.songMin.slider",(double) modConfig.minSongDelay / modConfig.maxSongDelay, modConfig.minSongDelay, this::updateSongMin);
        songMaxSlider = new configSlider(width/2,100,150,20, "timm.config.songMax.slider",(double) modConfig.maxSongDelay / (36000-modConfig.minSongDelay), modConfig.maxSongDelay, this::updateSongMax);



        debugLogs = new CheckboxWidget(width/2, 130, 20, 20, Text.literal(""), modConfig.debugLogging);
        addDrawableChild(debugLogs);

        addDrawableChild(new closeButton(this)); // TODO: remove the class for this it isn't really necessary
        // TODO: add a "load defaults" button

        addDrawableChild(menuMinSlider);
        addDrawableChild(menuMaxSlider);
        addDrawableChild(songMinSlider);
        addDrawableChild(songMaxSlider);

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

    private long updateSongMin(double value) {
        long realVal = lerp(1, modConfig.maxSongDelay, value);
        String valString = String.valueOf(realVal);
        modConfig.minSongDelay = realVal;
        modConfig.configMap.replace("songMinDelay", new String[]{"long", valString});
        configManager.update_cfg();
        return realVal;
    }

    private long updateSongMax(double value) {
        long realVal = lerp(modConfig.minSongDelay, 36000, value);
        String valString = String.valueOf(realVal);
        modConfig.maxSongDelay = realVal;
        modConfig.configMap.replace("songMaxDelay", new String[]{"long", valString});
        configManager.update_cfg();
        return realVal;
    }

}
