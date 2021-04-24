package me.cubert3d.palladium.gui.widget;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import me.cubert3d.palladium.util.annotation.UtilityClass;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/20/2021",
        status = "in-progress"
)

@UtilityClass
public final class DrawHelper {

    private DrawHelper() {}

    public static int getWindowColor() {
        return Colors.BACKGROUND_LAVENDER;
    }

    public static int getBorderColor() {
        return Colors.BACKGROUND_WHITE;
    }

    public static int getTextHeight() {
        return Common.getMC().textRenderer.fontHeight;
    }

    public static int getPaddedTextHeight() {
        return getTextHeight() + 2;
    }



    public static void drawBox(MatrixStack matrices, int x, int y, int width, int height, int color) {
        /*
        The widget draw methods (such as this) use position + dimension
        of the widget, whereas MC's draw methods use absolute position
        (two absolute coordinate pairs) to determine where to draw.
         */
        DrawableHelper.fill(matrices, x, y, x + width, y + height, color);
    }

    public static void drawWindow(MatrixStack matrices, int x, int y, int width, int height, int color) {

        int x2 = x + width;
        int y2 = y + height;

        // Main box
        DrawableHelper.fill(matrices, x + 1, y + 1, x2 - 1, y2 - 1, color);

        // Top border
        DrawableHelper.fill(matrices, x, y, x2 - 1, y + 1, getBorderColor());

        // Right Border
        DrawableHelper.fill(matrices, x2 - 1, y, x2, y2 - 1, getBorderColor());

        // Bottom Border
        DrawableHelper.fill(matrices, x + 1, y2 - 1, x2, y2, getBorderColor());

        // Left Border
        DrawableHelper.fill(matrices, x, y + 1, x + 1, y2, getBorderColor());
    }

    public static void drawLabeledWindow(MatrixStack matrices, String label, int x, int y, int width, int height, int color) {

        int x2 = x + width;
        int y2 = y + height;

        // Top box
        DrawableHelper.fill(matrices, x, y, x2 - 1, y + getPaddedTextHeight(), getBorderColor());

        // Label
        drawText(matrices, label, x + 1, y + 1, width - 2, getTextHeight(), Colors.BLACK);

        // Main box
        DrawableHelper.fill(matrices, x + 1, y + getPaddedTextHeight(), x2 - 1, y2 - 1, color);

        // Right Border
        DrawableHelper.fill(matrices, x2 - 1, y + getPaddedTextHeight(), x2, y2 - 1, getBorderColor());

        // Bottom Border
        DrawableHelper.fill(matrices, x + 1, y2 - 1, x2, y2, getBorderColor());

        // Left Border
        DrawableHelper.fill(matrices, x, y + getPaddedTextHeight(), x + 1, y2 - 1, getBorderColor());
    }

    public static void drawText(MatrixStack matrices, String text, int x, int y, int width, int height, int color) {
        Common.getMC().textRenderer.draw(matrices, text, x, y, color);
    }
}
