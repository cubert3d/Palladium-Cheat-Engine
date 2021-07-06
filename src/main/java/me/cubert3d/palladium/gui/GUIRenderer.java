package me.cubert3d.palladium.gui;

import net.minecraft.client.util.math.MatrixStack;

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
