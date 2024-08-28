/*
package net.tape.timm.gui.widget;

import com.terraformersmc.modmenu.gui.widget.entries.ModListEntry;
import com.terraformersmc.modmenu.util.mod.Mod;
import com.terraformersmc.modmenu.util.mod.fabric.FabricIconHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.text.Text;
import net.tape.timm.audio.Song;
import net.tape.timm.aws.UpdateEntry;
import net.tape.timm.gui.UpdateConfirmScreen;
import net.tape.timm.timmMain;

import java.io.File;
import java.util.HashSet;
import java.util.List;

public class UpdatesListWidget extends AlwaysSelectedEntryListWidget<UpdatesWidgetEntry> implements AutoCloseable {

    private final UpdateConfirmScreen parent;
    private List<UpdateEntry> entries = null;
    private HashSet<UpdateEntry> addedEntries = new HashSet<>();
    private UpdateEntry selectedEntry;
    private final FabricIconHandler iconHandler = new FabricIconHandler();



    UpdatesListWidget(int width, int height, int yPos, int itemHeight, UpdatesListWidget list, UpdateConfirmScreen parent) {
        super(timmMain.mc, width, height, yPos, itemHeight);
        this.parent = parent;
        if (list != null) {
            this.entries = list.entries;
        }
    }

    @Override
    public void setScrollAmount(double amount) {
        super.setScrollAmount(amount);
        int denominator = Math.max(0, this.getMaxPosition() - (this.getBottom() - this.getY() - 4));
        if (denominator <= 0) {
            parent.updateScrollPercent(0);
        } else {
            parent.updateScrollPercent(getScrollAmount() / Math.max(0, this.getMaxPosition() - (this.getBottom() - this.getY() - 4)));
        }
    }

    @Override
    public boolean isFocused() {
        return parent.getFocused() == this;
    }

    public void select(UpdatesWidgetEntry entry) {
        this.setSelected(entry);
        if (entry != null) {
            Song song = entry.getUpdateEntry().song();
            this.client.getNarratorManager().narrate(Text.translatable("narrator.select", song.getSongName()).getString());
        }
    }

    @Override
    public void setSelected(UpdatesWidgetEntry entry) {
        super.setSelected(entry);
        selectedEntry = entry.getUpdateEntry();
        parent.updateSelectedEntry(getSelectedOrNull());
    }

    @Override
    protected boolean isSelectedEntry(int index) {
        UpdatesWidgetEntry selected = getSelectedOrNull();
        return selected != null && selected.getUpdateEntry().equals(getEntry(index).getUpdateEntry());
    }

    @Override
    public int addEntry(UpdatesWidgetEntry entry) {
        if (addedEntries.contains(entry.updateEntry)) {
            return 0;
        }
        addedEntries.add(entry.updateEntry);
        int i = super.addEntry(entry);
        if (entry.getUpdateEntry().equals(selectedEntry)) {
            setSelected(entry);
        }
        return i;
    }

    @Override
    protected boolean removeEntry(UpdatesWidgetEntry entry) {
        addedEntries.remove(entry.updateEntry);
        return super.removeEntry(entry);
    }

    @Override
    protected UpdatesWidgetEntry remove(int index) {
        addedEntries.remove(getEntry(index).updateEntry);
        return super.remove(index);
    }

    @Override
    public void close() {
        iconHandler.close();
    }






}
*/
