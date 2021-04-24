package me.cubert3d.palladium.gui;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.modules.gui.PalladiumHudModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/9/2021",
        status = "in-progress"
)

public final class TextHudRenderer {

    private static final TextRenderer textRenderer = Common.getMC().textRenderer;
    private static final HudTextManager textManager = new HudTextManager();

    private TextHudRenderer() {}

    public static HudTextManager getTextManager() {
        return textManager;
    }

    public static boolean shouldRender() {

        // Only render the text-HUD if the HUD module is enabled and the f3 menu is not enabled.
        return ModuleManager.isModuleEnabled(PalladiumHudModule.class)
                && !Common.getOptions().debugEnabled
                && !ClickGUI.isOpen();
    }

    private static void drawText(MatrixStack matrices, @NotNull ColorText text, int x, int y) {
        int x1 = x - 1;
        int y1 = y - 1;
        int x2 = x1 + textRenderer.getWidth(text.getString()) + 2;
        int y2 = y1 + textRenderer.fontHeight;

        DrawableHelper.fill(matrices, x1, y1, x2, y2, text.getBackgroundColor());
        textRenderer.draw(matrices, text.getString(), x, y, text.getTextColor());
    }

    private static void drawVerticalLine(MatrixStack matrices, int x, int y1, int y2, int color) {
        if (y2 < y1) {
            int i = y1;
            y1 = y2;
            y2 = i;
        }
        DrawableHelper.fill(matrices, x, y1 + 1, x + 1, y2, color);
    }

    public static void drawTopLeftList(MatrixStack matrices) {
        textManager.getTopLeftList().ifPresent(textList -> {
            ArrayList<ColorText> list = textList.getAll();
            for (int i = 0; i < list.size(); i++) {
                ColorText text = list.get(i);

                int x = 1;
                int y = 1 + ((textRenderer.fontHeight) * i);

                drawText(matrices, text, x, y);
            }
        });
    }

    private static void drawTopRightList(MatrixStack matrices) {
        textManager.getTopRightList().ifPresent(textList -> {
            ArrayList<ColorText> list = textList.getAll();
            for (int i = 0; i < list.size(); i++) {
                ColorText text = list.get(i);

                int x = Common.getMC().getWindow().getScaledWidth() - textRenderer.getWidth(text.getString()) - 1;
                int y = 1 + (textRenderer.fontHeight * i);

                int b_x = x - 2;
                int b_y1 = y - 1;
                int b_y2 = b_y1 + textRenderer.fontHeight;

                drawText(matrices, text, x, y);
            }
        });
    }

    private static void drawBottomRightList(MatrixStack matrices) {
        textManager.getBottomRightList().ifPresent(textList -> {
            // Bottom-right just uses the body without the header.
            ArrayList<ColorText> list = textList.getBody();
            for (int i = 0; i < list.size(); i++) {
                ColorText text = list.get(i);

                int x = Common.getMC().getWindow().getScaledWidth() - textRenderer.getWidth(text.getString()) - 1;
                int y = (Common.getMC().getWindow().getScaledHeight() - textRenderer.fontHeight) - (textRenderer.fontHeight * i);

                drawText(matrices, text, x, y);
            }
        });
    }

    // Master render method that uses all the other methods to draw everything.
    public static void render(MatrixStack matrices) {
        drawTopLeftList(matrices);
        drawTopRightList(matrices);
        drawBottomRightList(matrices);
    }
}
