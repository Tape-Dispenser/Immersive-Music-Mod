
package net.tape.timm.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.terraformersmc.modmenu.util.mod.fabric.FabricIconHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.render.*;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.tape.timm.audio.Song;
import net.tape.timm.aws.UpdateEntry;
import net.tape.timm.gui.UpdateConfirmScreen;
import net.tape.timm.timmMain;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class UpdatesListWidget extends AlwaysSelectedEntryListWidget<UpdatesWidgetEntry> implements AutoCloseable {

    private final UpdateConfirmScreen parent;
    private List<UpdateEntry> entries = null;
    private HashSet<UpdateEntry> addedEntries = new HashSet<>();
    private UpdateEntry selectedEntry;
    private final FabricIconHandler iconHandler = new FabricIconHandler();
    private boolean scrolling;


    public UpdatesListWidget(int width, int height, int yPos, int itemHeight, UpdatesListWidget list, UpdateConfirmScreen parent) {
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
            //parent.updateScrollPercent(0);
        } else {
            //parent.updateScrollPercent(getScrollAmount() / Math.max(0, this.getMaxPosition() - (this.getBottom() - this.getY() - 4)));
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
        //parent.updateSelectedEntry(getSelectedOrNull());
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

    @Override
    protected void renderList(DrawContext DrawContext, int mouseX, int mouseY, float delta) {
        int entryCount = this.getEntryCount();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        for (int index = 0; index < entryCount; ++index) {
            int entryTop = this.getRowTop(index) + 2;
            int entryBottom = this.getRowTop(index) + this.itemHeight;
            if (entryBottom >= this.getY() && entryTop <= this.getBottom()) {
                int entryHeight = this.itemHeight - 4;
                UpdatesWidgetEntry entry = this.getEntry(index);
                int rowWidth = this.getRowWidth();
                int entryLeft;
                if (this.isSelectedEntry(index)) {
                    entryLeft = getRowLeft() - 2 + entry.getXOffset();
                    int selectionRight = this.getRowLeft() + rowWidth + 2;
                    float float_2 = this.isFocused() ? 1.0F : 0.5F;
                    RenderSystem.setShader(GameRenderer::getPositionProgram);
                    RenderSystem.setShaderColor(float_2, float_2, float_2, 1.0F);
                    Matrix4f matrix = DrawContext.getMatrices().peek().getPositionMatrix();
                    buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
                    buffer.vertex(matrix, entryLeft, entryTop + entryHeight + 2, 0.0F).next();
                    buffer.vertex(matrix, selectionRight, entryTop + entryHeight + 2, 0.0F).next();
                    buffer.vertex(matrix, selectionRight, entryTop - 2, 0.0F).next();
                    buffer.vertex(matrix, entryLeft, entryTop - 2, 0.0F).next();
                    tessellator.draw();
                    RenderSystem.setShader(GameRenderer::getPositionProgram);
                    RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
                    buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
                    buffer.vertex(matrix, entryLeft + 1, entryTop + entryHeight + 1, 0.0F).next();
                    buffer.vertex(matrix, selectionRight - 1, entryTop + entryHeight + 1, 0.0F).next();
                    buffer.vertex(matrix, selectionRight - 1, entryTop - 1, 0.0F).next();
                    buffer.vertex(matrix, entryLeft + 1, entryTop - 1, 0.0F).next();
                    tessellator.draw();
                }

                entryLeft = this.getRowLeft();
                entry.render(DrawContext, index, entryTop, entryLeft, rowWidth, entryHeight, mouseX, mouseY, this.isMouseOver(mouseX, mouseY) && Objects.equals(this.getEntryAtPos(mouseX, mouseY), entry), delta);
            }
        }
    }

    public void ensureVisible(UpdatesWidgetEntry entry) {
        super.ensureVisible(entry);
    }

    @Override
    protected void updateScrollingState(double double_1, double double_2, int int_1) {
        super.updateScrollingState(double_1, double_2, int_1);
        this.scrolling = int_1 == 0 && double_1 >= (double) this.getScrollbarPositionX() && double_1 < (double) (this.getScrollbarPositionX() + 6);
    }

    @Override
    public boolean mouseClicked(double double_1, double double_2, int int_1) {
        this.updateScrollingState(double_1, double_2, int_1);
        if (!this.isMouseOver(double_1, double_2)) {
            return false;
        } else {
            UpdatesWidgetEntry entry = this.getEntryAtPos(double_1, double_2);
            if (entry != null) {
                if (entry.mouseClicked(double_1, double_2, int_1)) {
                    this.setFocused(entry);
                    this.setDragging(true);
                    return true;
                }
            } else if (int_1 == 0 && this.clickedHeader((int) (double_1 - (double) (this.getX() + this.width / 2 - this.getRowWidth() / 2)), (int) (double_2 - (double) this.getY()) + (int) this.getScrollAmount() - 4)) {
                return true;
            }

            return this.scrolling;
        }
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_UP || keyCode == GLFW.GLFW_KEY_DOWN) {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
        if (getSelectedOrNull() != null) {
            return getSelectedOrNull().keyPressed(keyCode, scanCode, modifiers);
        }
        return false;
    }

    public final UpdatesWidgetEntry getEntryAtPos(double x, double y) {
        int int_5 = MathHelper.floor(y - (double) this.getY()) - this.headerHeight + (int) this.getScrollAmount() - 4;
        int index = int_5 / this.itemHeight;
        return x < (double) this.getScrollbarPositionX() && x >= (double) getRowLeft() && x <= (double) (getRowLeft() + getRowWidth()) && index >= 0 && int_5 >= 0 && index < this.getEntryCount() ? this.children().get(index) : null;
    }

    @Override
    protected int getScrollbarPositionX() {
        return this.width - 6;
    }

    @Override
    public int getRowWidth() {
        return this.width - (Math.max(0, this.getMaxPosition() - (this.getBottom() - this.getY() - 4)) > 0 ? 18 : 12);
    }

    @Override
    public int getRowLeft() {
        return this.getX() + 6;
    }

    public int getWidth() {
        return width;
    }

    public int getTop() {
        return this.getY();
    }

    public UpdateConfirmScreen getParent() {
        return parent;
    }

    @Override
    protected int getMaxPosition() {
        return super.getMaxPosition() + 4;
    }
}

