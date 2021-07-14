package me.cubert3d.palladium.gui;

import me.cubert3d.palladium.util.Common;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

@ClassInfo(
        description = "Various methods for drawing GUI elements.",
        authors = "cubert3d",
        date = "4/20/2021",
        type = ClassType.UTILITY
)

public final class DrawHelper {

    private DrawHelper() {}

    public static int getTextHeight() {
        return Common.getMC().textRenderer.fontHeight;
    }

    private static int getEllipsesWidth() {
        return Common.getMC().textRenderer.getWidth("...");
    }



    public static void drawBox(MatrixStack matrices, int x1, int y1, int x2, int y2, int color) {
        /*
        The widget draw methods (such as this) use position + dimension
        of the widget, whereas MC's draw methods use absolute position
        (two absolute coordinate pairs) to determine where to draw.
         */
        DrawableHelper.fill(matrices, x1, y1, x2, y2, color);
    }

    public static void drawHorizontalLine(MatrixStack matrices, int x1, int x2, int y, int color) {
        drawBox(matrices, x1, y, x2, y + 1, color);
    }

    public static void drawVerticalLine(MatrixStack matrices, int x, int y1, int y2, int color) {
        drawBox(matrices, x, y1, x + 1, y2, color);
    }

    public static void drawOutlineBox(MatrixStack matrices, int x1, int y1, int x2, int y2, int color) {

        // Top
        drawHorizontalLine(matrices, x1, x2 - 1, y1, color);

        // Right
        drawVerticalLine(matrices, x2 - 1, y1, y2 - 1, color);

        // Bottom
        drawHorizontalLine(matrices, x1 + 1, x2, y2 - 1, color);

        // Left
        drawVerticalLine(matrices, x1, y1 + 1, y2, color);
    }

    public static void drawText(MatrixStack matrices, String text, int x, int y, int width, int height, int color) {

        int textWidth = Common.getMC().textRenderer.getWidth(text);

        if (textWidth > width) {
            String trimmedText = text;
            int trimmedTextWidth;

            for (int i = text.length(); i > 0; i--) {
                trimmedText = trimmedText.substring(0, i);
                trimmedTextWidth = Common.getMC().textRenderer.getWidth(trimmedText) + getEllipsesWidth();
                if (trimmedTextWidth <= width)
                    break;
            }

            trimmedText = trimmedText.concat("...");
            Common.getMC().textRenderer.draw(matrices, trimmedText, x, y, color);
        }
        else {
            Common.getMC().textRenderer.draw(matrices, text, x, y, color);
        }
    }
}
