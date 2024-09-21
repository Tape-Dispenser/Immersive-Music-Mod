package net.tape.timm.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;
import net.tape.timm.aws.CheckUpdates;
import net.tape.timm.configManager;
import net.tape.timm.gui.widget.SimpleButton;
import net.tape.timm.modConfig;
import net.tape.timm.timmMain;

import static net.tape.timm.timmMain.mc;

public class UpdatePromptScreen extends Screen{
    public UpdatePromptScreen(Screen parent) {
        super(Text.translatable("timm.update.prompt.text"));
        this.parent = parent;
    }

    int txtcol = 0xffffff;
    private final Screen parent;

    private boolean accepted = false;

    private SimpleButton acceptButton;
    private SimpleButton declineButton;
    private SimpleButton cancelButton;

    private CheckboxWidget alwaysUpdate;

    private CheckUpdates updateChecker;

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
        updateChecker = new CheckUpdates();

        alwaysUpdate = CheckboxWidget.builder(Text.translatable("timm.update.prompt.alwaysUpdate.text"), mc.textRenderer)
                .checked(modConfig.alwaysCheckUpdates)
                .callback((checkbox, checked) -> setAlwaysCheckUpdates(checked))
                .tooltip(Tooltip.of(Text.translatable("timm.update.prompt.alwaysUpdate.tooltip")))
                .build();
        alwaysUpdate.setPosition(10, this.height - 60);
        alwaysUpdate.visible = true;
        alwaysUpdate.setAlpha(1.0f);

        addDrawableChild(alwaysUpdate);

        acceptButton = new SimpleButton(
                this,
                button -> acceptButtonClick(),
                "timm.update.prompt.accept.text",
                "timm.update.prompt.accept.tooltip",
                10,
                -10
        );
        addDrawableChild(acceptButton);

        declineButton = new SimpleButton(
                this,
                button -> this.close(),
                "timm.update.prompt.decline.text",
                "timm.update.prompt.decline.tooltip",
                -10,
                -10
        );
        addDrawableChild(declineButton);

        cancelButton = new SimpleButton(
                this,
                button -> cancelButtonClick(),
                "timm.update.prompt.cancel.text",
                "timm.update.prompt.cancel.tooltip",
                this.width / 2 - (ButtonWidget.DEFAULT_WIDTH_SMALL / 2),
                -10
        );

        if (modConfig.alwaysCheckUpdates) {
            acceptButtonClick();
        }
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(ctx);
        super.render(ctx, mouseX, mouseY, delta);

        if (!accepted) {
            ctx.drawTextWrapped(mc.textRenderer, Text.translatable("timm.update.prompt.message"), 10, 30, this.width - 10, txtcol);
        } else {
            ctx.drawCenteredTextWithShadow(mc.textRenderer, Text.translatable("timm.update.prompt.waiting"), this.width/2, 50, txtcol);
            if (!updateChecker.isAlive()) {
                timmMain.LOGGER.info("Check for updates Successful");
                // set screen to update confirm screen
                mc.setScreen(new UpdateConfirmScreen(this.parent));
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
        remove(alwaysUpdate);
        addDrawableChild(cancelButton);
    }

    public void cancelButtonClick() {
        if (updateChecker.isAlive()) {
            updateChecker.interrupt();
            updateChecker.getUncaughtExceptionHandler();
            this.close();
        }
    }

    private void setAlwaysCheckUpdates(boolean check) {
        modConfig.alwaysCheckUpdates = check;
        String checkString = String.valueOf(check);
        modConfig.configMap.replace("alwaysCheckUpdates", checkString);
        configManager.update_cfg(modConfig.configMap);
    }
}
