package me.cubert3d.palladium.gui;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.modules.gui.PalladiumHudModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/9/2021",
        status = "in-progress"
)

public final class HudRenderer {

    private static final TextRenderer textRenderer = Common.getMC().textRenderer;
    private static final HudTextManager textManager = new HudTextManager();

    public static HudTextManager getTextManager() {
        return textManager;
    }

    public static boolean shouldRender() {
        return ModuleManager.isModuleEnabled(PalladiumHudModule.class)
                && !Common.getOptions().debugEnabled;
    }

    private static void drawText(MatrixStack matrices, String text, int x, int y) {
        drawText(matrices, text, x, y, TextColors.COLOR_WHITE, TextColors.BACKGROUND_COLOR_LAVENDER);
    }

    private static void drawText(MatrixStack matrices, String text, int x, int y, int color, int backgroundColor) {

        int x1 = x - 1;
        int y1 = y - 1;
        int x2 = x1 + textRenderer.getWidth(text) + 2;
        int y2 = y1 + textRenderer.fontHeight;

        DrawableHelper.fill(matrices, x1, y1, x2, y2, backgroundColor);
        textRenderer.draw(matrices, text, x, y, color);
    }

    private static void drawVerticalLine(MatrixStack matrices, int x, int y1, int y2, int color) {
        if (y2 < y1) {
            int i = y1;
            y1 = y2;
            y2 = i;
        }
        DrawableHelper.fill(matrices, x, y1 + 1, x + 1, y2, color);
    }

    public static void drawTopLeftStack(MatrixStack matrices) {
        for (int i = 0; i < textManager.getTopLeftStrings().size(); i++) {
            String string = textManager.getTopLeftStrings().get(i);

            int x = 1;
            int y = 1 + ((textRenderer.fontHeight) * i);

            drawText(matrices, string, x, y);
        }
    }

    private static void drawTopRightStack(MatrixStack matrices) {
        for (int i = 0; i < textManager.getTopRightStrings().size(); i++) {
            String string = textManager.getTopRightStrings().get(i);

            int x = Common.getMC().getWindow().getScaledWidth() - textRenderer.getWidth(string) - 1;
            int y = 1 + ((textRenderer.fontHeight) * i);

            /* Used for the border line.
            int b_x = x - 2;
            int b_y1 = y - 1;
            int b_y2 = b_y1 + textRenderer.fontHeight;
             */

            drawText(matrices, string, x, y);
        }
    }

    // Master render method that uses all the other methods to draw everything.
    public static void render(MatrixStack matrices) {
        drawTopLeftStack(matrices);
        drawTopRightStack(matrices);
    }
}
