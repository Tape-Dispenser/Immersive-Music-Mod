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
import net.tape.timm.util.math;
import java.math.RoundingMode;

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

        menuMinSlider = new configSlider(
                width/2,
                30,
                150,
                20,
                "timm.config.menuMin.slider",
                math.invLerp(1, 36000, modConfig.minMenuDelay),
                makeTicksReadable(modConfig.minMenuDelay),
                this::updateMenuMin,
                this::makeTicksReadable
        );
        menuMaxSlider = new configSlider(
                width/2,
                50,
                150,
                20,
                "timm.config.menuMax.slider",
                math.invLerp(1, 36000, modConfig.maxMenuDelay),
                makeTicksReadable(modConfig.maxMenuDelay),
                this::updateMenuMax,
                this::makeTicksReadable
        );
        songMinSlider = new configSlider(
                width/2,
                80,
                150,
                20,
                "timm.config.songMin.slider",
                math.invLerp(1, 36000, modConfig.minGameDelay),
                makeTicksReadable(modConfig.minGameDelay),
                this::updateGameMin,
                this::makeTicksReadable
        );
        songMaxSlider = new configSlider(
                width/2,
                100,
                150,
                20,
                "timm.config.songMax.slider",
                math.invLerp(1, 36000, modConfig.maxGameDelay),
                makeTicksReadable(modConfig.maxGameDelay),
                this::updateGameMax,
                this::makeTicksReadable
        );

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
        long tickVal = math.lerp(1, 36000, value);
        String valString = String.valueOf(tickVal);
        modConfig.minMenuDelay = tickVal;
        modConfig.configMap.replace("menuMinDelay", valString);
        configManager.update_cfg(modConfig.configMap);

        return tickVal;
    }

    private long updateMenuMax(double value) {
        long tickVal = math.lerp(1, 36000, value);
        String valString = String.valueOf(tickVal);
        modConfig.maxMenuDelay = tickVal;
        modConfig.configMap.replace("menuMaxDelay", valString);
        configManager.update_cfg(modConfig.configMap);
        return tickVal;
    }

    private long updateGameMin(double value) {
        long tickVal = math.lerp(1, 36000, value);
        String valString = String.valueOf(tickVal);
        modConfig.minGameDelay = tickVal;
        modConfig.configMap.replace("gameMinDelay", valString);
        configManager.update_cfg(modConfig.configMap);
        return tickVal;
    }

    private long updateGameMax(double value) {
        long tickVal = math.lerp(1, 36000, value);
        String valString = String.valueOf(tickVal);
        modConfig.maxGameDelay = tickVal;
        modConfig.configMap.replace("gameMaxDelay", valString);
        configManager.update_cfg(modConfig.configMap);
        return tickVal;
    }

    private String makeTicksReadable(long ticks) {
        int seconds = Math.round((float) ticks / 20);
        if (seconds < 60) {
            return String.format("%d Seconds", seconds);
        }
        int minutes = seconds / 60;
        return String.format("%d Mins, %d Secs", minutes, seconds - minutes * 60);
    }

}
