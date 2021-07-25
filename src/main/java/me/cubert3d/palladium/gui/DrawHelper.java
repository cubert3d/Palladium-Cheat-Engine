package me.cubert3d.palladium.gui;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

@ClassInfo(
        description = "Various methods for drawing GUI elements.",
        authors = "cubert3d",
        date = "4/20/2021",
        type = ClassType.UTILITY
)

public final class DrawHelper {

    /**
     * The height, in pixels, of Minecraft's default font.
     */
    public static final int FONT_HEIGHT = 9;

    private DrawHelper() {}

    public static int getTextHeight() {
        return MinecraftClient.getInstance().textRenderer.fontHeight;
    }

    private static int getEllipsesWidth() {
        return MinecraftClient.getInstance().textRenderer.getWidth("...");
    }

    /**
     * <p>
     *     Draws a box given two pairs of coordinates. The first pair of coordinates represents the top left corner, while the second represents the bottom right.
     * </p>
     * <p>
     *     Like all {@code DrawHelper} methods, this method uses absolute position, not relative.
     * </p>
     * @param matrices the matrix stack to be used for drawing this box
     * @param x1 the position of the left side of this box
     * @param y1 the position of the top side of this box
     * @param x2 the position of the right side of this box
     * @param y2 the position of the bottom side of this box
     * @param color the color of the box to be drawn
     */
    public static void drawBox(MatrixStack matrices, int x1, int y1, int x2, int y2, int color) {
        DrawableHelper.fill(matrices, x1, y1, x2, y2, color);
    }

    /**
     * <p>
     *     Draws a vertical line, given two x-coordinates, and a y-coordinate. Line thickness is always one pixel.
     * </p>
     * <p>
     *     Like all {@code DrawHelper} methods, this method uses absolute position, not relative.
     * </p>
     * @param matrices the matrix stack to be used for drawing this line
     * @param x1 the x-position of the left end of this line
     * @param x2 the x-position of the right end of this line
     * @param y the y-position of this line
     * @param color the color of the line to be drawn
     */
    public static void drawHorizontalLine(MatrixStack matrices, int x1, int x2, int y, int color) {
        drawBox(matrices, x1, y, x2, y + 1, color);
    }

    /**
     * <p>
     *     Draws a vertical line, given an x-coordinate, and two y-coordinates. Line thickness is always one pixel.
     * </p>
     * <p>
     *     Like all {@code DrawHelper} methods, this method uses absolute position, not relative.
     * </p>
     * @param matrices the matrix stack to be used for drawing this line
     * @param x the x-position of this line
     * @param y1 the y-position of the top end of this line
     * @param y2 the y-position of the bottom end of this line
     * @param color the color of the line to be drawn
     */
    public static void drawVerticalLine(MatrixStack matrices, int x, int y1, int y2, int color) {
        drawBox(matrices, x, y1, x + 1, y2, color);
    }

    /**
     * <p>
     *     Draws a hollow box given two pairs of coordinates. The line thickness is always one pixel.
     * </p>
     * <p>
     *     Like all {@code DrawHelper} methods, this method uses absolute position, not relative.
     * </p>
     * @param matrices the matrix stack to be used for drawing this box
     * @param x1 the position of the left side of this box
     * @param y1 the position of the top side of this box
     * @param x2 the position of the right side of this box
     * @param y2 the position of the bottom side of this box
     * @param color the color of the box to be drawn
     */
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

    /**
     * <p>
     *     Draws a cross given two pairs of coordinates.
     * </p>
     * <p>
     *     Like all {@code DrawHelper} methods, this method uses absolute position, not relative.
     * </p>
     * @param matrices the matrix stack to be used for drawing this box
     * @param x1 the position of the left side of this cross
     * @param y1 the position of the top side of this cross
     * @param x2 the position of the right side of this cross
     * @param y2 the position of the bottom side of this cross
     * @param color the color of the cross to be drawn
     */
    public static void drawCross(MatrixStack matrices, int x1, int y1, int x2, int y2, int color) {
        int width = Math.abs(x2 - x1);
        for (int i = 0; i < width; i++) {
            int pixelX = x1 + i;
            int pixelY1 = y1 + i;
            int pixelY2 = y2 - i - 1;
            drawPixel(matrices, pixelX, pixelY1, color);
            if (pixelY2 != pixelY1) {
                drawPixel(matrices, pixelX, pixelY2, color);
            }
        }
    }

    /**
     * <p>
     *     Draws a pixel given a pair of coordinates.
     * </p>
     * <p>
     *     Like all {@code DrawHelper} methods, this method uses absolute position, not relative.
     * </p>
     * @param matrices the matrix stack to be used for drawing this pixel
     * @param x the x-position of this pixel
     * @param y the y-position of this pixel
     * @param color the color of the pixel to be drawn
     */
    public static void drawPixel(MatrixStack matrices, int x, int y, int color) {
        drawBox(matrices, x, y, x + 1, y + 1, color);
    }

    public static void drawText(MatrixStack matrices, String text, int x, int y, int width, int color) {

        int textWidth = MinecraftClient.getInstance().textRenderer.getWidth(text);

        if (textWidth > width) {
            String trimmedText = text;
            int trimmedTextWidth;

            for (int i = text.length(); i > 0; i--) {
                trimmedText = trimmedText.substring(0, i);
                trimmedTextWidth = MinecraftClient.getInstance().textRenderer.getWidth(trimmedText) + getEllipsesWidth();
                if (trimmedTextWidth <= width)
                    break;
            }

            trimmedText = trimmedText.concat("...");
            MinecraftClient.getInstance().textRenderer.draw(matrices, trimmedText, x, y, color);
        }
        else {
            MinecraftClient.getInstance().textRenderer.draw(matrices, text, x, y, color);
        }
    }
}
