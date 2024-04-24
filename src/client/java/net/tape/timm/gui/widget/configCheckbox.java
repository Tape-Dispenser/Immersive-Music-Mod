package net.tape.timm.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.tape.timm.configManager;
import net.tape.timm.modConfig;


@Environment(value=EnvType.CLIENT)
public class configCheckbox extends PressableWidget {
    private static final Identifier TEXTURE = new Identifier("textures/gui/checkbox.png");
    private boolean checked;
    private final String configKey;



    public configCheckbox(int x, int y, int width, int height, String cfgKey, boolean checked) {
        super(x, y, width, height, Text.literal(""));
        this.checked = checked;
        this.configKey = cfgKey;
    }

    @Override
    public void onPress() {
        this.checked = !this.checked;
        String checkString = String.valueOf(this.checked);
        modConfig.configMap.replace(configKey, new String[]{"bool", checkString});
        modConfig.copyVals(); // eventually some way to only copy one specific value from config map would be cool i think
        configManager.update_cfg();
    }

    public boolean isChecked() {
        return this.checked;
    }

    @Override
    public void appendClickableNarrations(NarrationMessageBuilder builder) {
        builder.put(NarrationPart.TITLE, (Text)this.getNarrationMessage());
        if (this.active) {
            if (this.isFocused()) {
                builder.put(NarrationPart.USAGE, Text.translatable("narration.checkbox.usage.focused"));
            } else {
                builder.put(NarrationPart.USAGE, Text.translatable("narration.checkbox.usage.hovered"));
            }
        }
    }

    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        drawTexture(matrices, this.getX(), this.getY(), this.isFocused() ? 20.0F : 0.0F, this.checked ? 20.0F : 0.0F, 20, this.height, 64, 64);

    }

}