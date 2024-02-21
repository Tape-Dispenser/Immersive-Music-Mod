package net.tape.timm.gui.widget;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget.NarrationSupplier;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.tape.timm.timmMain;

import java.util.function.Supplier;

public class closeButton extends ButtonWidget {

    public closeButton(Screen screen) {
        super(0, 0, 0, 0, Text.translatable("timm.config.close.text"), button -> screen.close(), ButtonWidget.DEFAULT_NARRATION_SUPPLIER);

        this.height = ButtonWidget.DEFAULT_HEIGHT;
        this.width = ButtonWidget.DEFAULT_WIDTH_SMALL;

        this.setX(screen.width - 10 - this.width);
        this.setY(screen.height - 10 - this.height);

        this.setTooltip(Tooltip.of(Text.translatable("timm.config.close.tooltip")));
    }
}
