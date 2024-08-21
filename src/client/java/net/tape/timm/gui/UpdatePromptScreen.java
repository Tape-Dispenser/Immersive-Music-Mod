package net.tape.timm.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.tape.timm.aws.CheckUpdates;
import net.tape.timm.aws.GetUpdatesReturn;
import net.tape.timm.aws.getSongs;
import net.tape.timm.gui.widget.SimpleButton;
import net.tape.timm.timmMain;

import java.io.File;

import static net.tape.timm.timmMain.mc;

public class UpdatePromptScreen extends Screen{
    public UpdatePromptScreen(Screen parent) {
        super(Text.translatable("timm.update.text"));
        this.parent = parent;
    }

    int txtcol = 0xffffff;
    private final Screen parent;

    private boolean accepted = false;

    private SimpleButton acceptButton;
    private SimpleButton declineButton;
    private SimpleButton cancelButton;

    private CheckUpdates updateChecker = new CheckUpdates();

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void init() {

        acceptButton = new SimpleButton(
                this,
                button -> acceptButtonClick(),
                "timm.update.accept.text",
                "timm.update.accept.tooltip",
                10,
                -10
        );
        addDrawableChild(acceptButton);

        declineButton = new SimpleButton(
                this,
                button -> this.close(),
                "timm.update.decline.text",
                "timm.update.decline.tooltip",
                -10,
                -10
        );
        addDrawableChild(declineButton);

        cancelButton = new SimpleButton(
                this,
                button -> cancelButtonClick(),
                "timm.update.cancel.text",
                "timm.update.cancel.tooltip",
                this.width / 2 - (ButtonWidget.DEFAULT_WIDTH_SMALL / 2),
                -10
        );
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(ctx);
        super.render(ctx, mouseX, mouseY, delta);

        if (!accepted) {
            ctx.drawTextWrapped(mc.textRenderer, Text.translatable("timm.update.message"), 10, 30, this.width - 10, txtcol);
        } else {
            ctx.drawTextWrapped(mc.textRenderer, Text.translatable("timm.update.waiting"), 10, 30, this.width - 10, txtcol);
            if (!updateChecker.isAlive()) {
                timmMain.LOGGER.info("Check for updates Successful");
                // set screen to update confirm screen
                this.close();
            }
        }


    }

    @Override
    public void close() {
        mc.setScreen(parent);
    }

    public void acceptButtonClick() {
        updateChecker.start();
        accepted = true;
        remove(acceptButton);
        remove(declineButton);
        addDrawableChild(cancelButton);
    }

    public void cancelButtonClick() {
        if (updateChecker.isAlive()) {
            updateChecker.interrupt();
            updateChecker.getUncaughtExceptionHandler();
            this.close();
        }
    }
}
