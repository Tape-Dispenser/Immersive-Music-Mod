package net.tape.timm.gui.widget;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.tape.timm.gui.UpdatePromptScreen;

public class SimpleButton extends ButtonWidget {

    public SimpleButton(Screen screen, ButtonWidget.PressAction onClick, String textTransKey, String tooltipTransKey, int xPos, int yPos) {
        super(0, 0, 0, 0, Text.translatable(textTransKey), onClick, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);

        this.height = ButtonWidget.DEFAULT_HEIGHT;
        this.width = ButtonWidget.DEFAULT_WIDTH_SMALL;

        if (xPos < 0) {
            xPos = screen.width - this.width + xPos; // add cuz negative
        }

        if (yPos < 0) {
            yPos = screen.height - this.height + yPos;
        }

        this.setX(xPos);
        this.setY(yPos);

        this.setTooltip(Tooltip.of(Text.translatable(tooltipTransKey)));
    }
}
