package net.tape.timm.gui.widget;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.tape.timm.gui.UpdatePromptScreen;

public class AcceptButton extends ButtonWidget {

    public AcceptButton(UpdatePromptScreen screen) {
        super(0, 0, 0, 0, Text.translatable("timm.update.accept"), button -> screen.acceptButtonClick(), ButtonWidget.DEFAULT_NARRATION_SUPPLIER);

        this.height = ButtonWidget.DEFAULT_HEIGHT;
        this.width = ButtonWidget.DEFAULT_WIDTH_SMALL;

        this.setX(10);
        this.setY(screen.height - 10 - this.height);

        this.setTooltip(Tooltip.of(Text.translatable("timm.config.close.tooltip")));
    }
}
