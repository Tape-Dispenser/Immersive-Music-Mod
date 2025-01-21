
package net.tape.timm.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.Util;
import net.tape.timm.aws.UpdateEntry;
import net.tape.timm.timmMain;

public class UpdatesListEntry extends AlwaysSelectedEntryListWidget.Entry<UpdatesListEntry> {
    protected final MinecraftClient client;
    protected UpdateEntry updateEntry;
    protected final UpdatesListWidget parentList;
    protected long lastClickAt;

    public UpdatesListEntry(UpdateEntry entry, UpdatesListWidget parentList) {
        this.updateEntry = entry;
        this.parentList = parentList;
        this.client = MinecraftClient.getInstance();

    }

    private static final Identifier CHECK_LOCATION = Identifier.of("timm", "textures/gui/check.png");
    private static final Identifier X_LOCATION = Identifier.of("timm", "textures/gui/x.png");

    @Override
    public Text getNarration() {
        return Text.literal(this.updateEntry.getSong().getSongName());
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        x += getXOffset();
        entryWidth -= getXOffset();
        int iconSize = 32;
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        context.drawTexture(this.getIconTexture(), x, y, 0.0F, 0.0F, iconSize, iconSize, iconSize, iconSize);
        RenderSystem.disableBlend();

        // render icon to say if entry is disabled or not
        Identifier icon = CHECK_LOCATION;
        RenderSystem.setShaderColor(0.0F, 1.0F, 0.0F, 1.0F);
        if (!this.updateEntry.isEnabled()) {
            RenderSystem.setShaderColor(1.0F, 0.0F, 0.0F, 1.0F);
            icon = X_LOCATION;
        }
        RenderSystem.enableBlend();
        context.drawTexture(icon, entryWidth-iconSize, y, 0.0F, 0.0F, iconSize, iconSize, iconSize, iconSize);
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        // text rendering logic
        TextRenderer font = this.client.textRenderer;
        int maxTextWidth = entryWidth - (iconSize * 2) - 6;
        // trim text lengths if needed
        StringVisitable trimmedName = trimText(updateEntry.getSong().getSongName(), font, maxTextWidth);
        StringVisitable trimmedAuthor = trimText(String.format("By: %s", updateEntry.getSong().getAuthor()), font, maxTextWidth);
        StringVisitable trimmedFile = trimText(String.format("File name: %s", updateEntry.getSong().getPathOrId()), font, maxTextWidth);
        // render the text
        context.drawText(font, Language.getInstance().reorder(trimmedName), x + iconSize + 3, y + 1, 0xFFFFFF, false);
        context.drawText(font, Language.getInstance().reorder(trimmedAuthor), x + iconSize + 3, y + 1 + 10, 0xAAAAAA, false);
        context.drawText(font, Language.getInstance().reorder(trimmedFile), x + iconSize + 3, y + 1 + 20, 0xAAAAAA, false);
        // render icon on right side to say if its disabled or not


    }

    public StringVisitable trimText(String text, TextRenderer font, int maxWidth) {
        StringVisitable output = Text.literal(text);
        if (font.getWidth(output) > maxWidth) {
            StringVisitable ellipsis = StringVisitable.plain("...");
            output = StringVisitable.concat(font.trimToWidth(output, maxWidth - font.getWidth(ellipsis)), ellipsis);
        }
        return output;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int delta) {
        parentList.select(this);
        timmMain.LOGGER.info(String.format("Clicked %s", this.updateEntry.getFile()));

        if (Util.getMeasuringTimeMs() - this.lastClickAt < 250) {
            // double click, toggle this entry from download list
            this.updateEntry.setEnabled(!this.updateEntry.isEnabled());
            String statusWord = this.updateEntry.isEnabled() ? "enabled" : "disabled";
            timmMain.LOGGER.info(String.format("%s entry %s", statusWord, this.updateEntry.getFile()));
        }
        this.lastClickAt = Util.getMeasuringTimeMs();
        return true;
    }


    public int getXOffset() {
        return 0;
    }

    public Identifier getIconTexture() {
        return new Identifier("textures/misc/unknown_server.png");
    }

    public UpdateEntry getUpdateEntry() {
        return this.updateEntry;
    }

    public void enable() {
        this.updateEntry.setEnabled(true);
    }

    public void disable() {
        this.updateEntry.setEnabled(false);
    }
}
