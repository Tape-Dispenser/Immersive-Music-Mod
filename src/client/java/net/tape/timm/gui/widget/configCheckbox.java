package net.tape.timm.gui.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;

public class configCheckbox extends CheckboxWidget {
    public configCheckbox(int x, int y, int width, int height, Text message, boolean checked, boolean showMessage, PressAction lambda) {
        super(x, y, width, height, message, checked, showMessage);
        this.lambda = lambda;

    }

    final protected PressAction lambda;

    @Override
    public void onPress() {
        this.lambda.onPress(this.isChecked());
    }


    @Environment(value= EnvType.CLIENT)
    public static interface PressAction {
        public void onPress(boolean check);
    }
}
