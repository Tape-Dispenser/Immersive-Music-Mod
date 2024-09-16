
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

public class UpdatesListEntry extends AlwaysSelectedEntryListWidget.Entry<UpdatesListEntry> {
    protected final MinecraftClient client;
    protected final UpdateEntry updateEntry;
    protected final UpdatesListWidget parentList;
    protected long lastClickAt;

    UpdatesListEntry(UpdateEntry entry, UpdatesListWidget parentList) {
        this.updateEntry = entry;
        this.parentList = parentList;
        this.client = MinecraftClient.getInstance();

    }

    @Override
    public Text getNarration() {
        return Text.literal(this.updateEntry.song().getSongName());
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

        Text name = Text.literal(updateEntry.song().getSongName());
        StringVisitable trimmedName = name;
        int maxNameWidth = entryWidth - iconSize - 3;
        TextRenderer font = this.client.textRenderer;
        if (font.getWidth(name) > maxNameWidth) {
            StringVisitable ellipsis = StringVisitable.plain("...");
            trimmedName = StringVisitable.concat(font.trimToWidth(name, maxNameWidth - font.getWidth(ellipsis)), ellipsis);
        }
        context.drawText(font, Language.getInstance().reorder(trimmedName), x + iconSize + 3, y + 1, 0xFFFFFF, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int delta) {
        //parentList.select(this);

        if (Util.getMeasuringTimeMs() - this.lastClickAt < 250) {
            // double click, deselect this entry from download list
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
}
