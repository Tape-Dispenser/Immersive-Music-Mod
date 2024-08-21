package net.tape.timm.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.tape.timm.aws.GetUpdatesReturn;
import net.tape.timm.aws.getSongs;
import net.tape.timm.gui.widget.SimpleButton;
import net.tape.timm.timmMain;

import java.io.File;

import static net.tape.timm.timmMain.mc;

public class UpdatePromptScreen extends Screen {
    public UpdatePromptScreen(Screen parent) {
        super(Text.translatable("timm.update.text"));
        this.parent = parent;
    }

    int txtcol = 0xffffff;
    private final Screen parent;

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
        addDrawableChild(new SimpleButton(
                this,
                button -> acceptButtonClick(),
                "timm.update.accept.text",
                "timm.update.accept.tooltip",
                10,
                -10
        ));

        addDrawableChild(new SimpleButton(
                this,
                button -> this.close(),
                "timm.update.decline.text",
                "timm.update.decline.tooltip",
                -10,
                -10
        ));
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(ctx);
        super.render(ctx, mouseX, mouseY, delta);

        ctx.drawTextWrapped(mc.textRenderer, Text.translatable("timm.update.message"), 10, 30, this.width - 10, txtcol);
    }

    @Override
    public void close() {
        mc.setScreen(parent);
    }

    public void acceptButtonClick() {
        getSongs.initS3Client();
        GetUpdatesReturn updates = getSongs.checkForUpdates();
        if (updates == null) {
            timmMain.LOGGER.warn("Unknown error occurred while attempting to check for song updates!");
            this.close();
            return;
        }
        if (updates.filesToUpdate() == null) {
            this.close();
            return;
        }
        for (File file : updates.filesToUpdate()) {
            timmMain.LOGGER.info(String.format("Song \"%s\" needs to update", file.getPath()));
        }

        // somehow push this data to the next screen, or redraw the screen with new content (pls dont do that)

        this.close();
    }
}
