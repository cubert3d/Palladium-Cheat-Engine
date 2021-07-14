package me.cubert3d.palladium.gui;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.util.math.MatrixStack;

@ClassInfo(
        authors = "cubert3d",
        date = "7/4/2021",
        type = ClassType.RENDERER
)

public final class GUIRenderer {

    private final ClickGUI clickGUI;
    private final TextHudRenderer textHudRenderer;

    public GUIRenderer() {
        this.clickGUI = new ClickGUI();
        this.textHudRenderer = new TextHudRenderer();
    }

    public final void initialize() {
        this.getClickGUI().initialize();
    }

    public final ClickGUI getClickGUI() {
        return clickGUI;
    }

    public final TextHudRenderer getTextHudRenderer() {
        return textHudRenderer;
    }

    public final void render(MatrixStack matrices) {
        if (textHudRenderer.shouldRender()) {
            textHudRenderer.render(matrices);
        }
        clickGUI.render(matrices);
    }
}
