package net.tape.timm.gui.widget;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.tape.timm.configManager;
import net.tape.timm.gui.configScreen;
import net.tape.timm.modConfig;

public class resetButton extends ButtonWidget {

    public resetButton(configScreen screen) {

        super(0, 0, 0, 0, Text.translatable("timm.config.reset.text"), button -> screen.resetConfig(), ButtonWidget.DEFAULT_NARRATION_SUPPLIER);



        this.height = ButtonWidget.DEFAULT_HEIGHT;
        this.width = ButtonWidget.DEFAULT_WIDTH_SMALL;

        this.setX(10);
        this.setY(screen.height - 10 - this.height);

        this.setTooltip(Tooltip.of(Text.translatable("timm.config.reset.tooltip")));
    }
}