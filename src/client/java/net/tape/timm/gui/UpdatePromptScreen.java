package net.tape.timm.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.tape.timm.aws.GetUpdatesReturn;
import net.tape.timm.aws.getSongs;
import net.tape.timm.gui.widget.AcceptButton;
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
        addDrawableChild(new AcceptButton(this));
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        super.render(ctx, mouseX, mouseY, delta);

        this.renderBackgroundTexture(ctx);
        ctx.drawTextWrapped(mc.textRenderer, Text.translatable("timm.update.message"), 10, 30, this.width - 10, txtcol);
    }

    @Override
    public void close() {
        mc.setScreen(parent);
    }

    public void acceptButtonClick() {
        getSongs.initS3Client();
        GetUpdatesReturn updates = getSongs.checkForUpdates();
        if (updates.filesToUpdate() == null) {
            this.close();
            return;
        }
        for (File file : updates.filesToUpdate()) {
            timmMain.LOGGER.info(String.format("Song \"%s\" needs to update", file.getPath()));
        }
        this.close();
    }
}
