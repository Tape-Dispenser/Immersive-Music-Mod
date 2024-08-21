package net.tape.timm.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import static net.tape.timm.timmMain.mc;

public class UpdateConfirmScreen extends Screen {
    public UpdateConfirmScreen(Screen parent) {
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

    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {

    }

    @Override
    public void close() {
        mc.setScreen(parent);
    }
}
