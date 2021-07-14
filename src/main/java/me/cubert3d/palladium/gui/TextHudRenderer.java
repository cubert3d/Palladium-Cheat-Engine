package me.cubert3d.palladium.gui;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.module.modules.gui.PalladiumHudModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@ClassInfo(
        description = "Renders the text HUD.",
        authors = "cubert3d",
        date = "4/9/2021",
        type = ClassType.RENDERER
)

public final class TextHudRenderer {

    private final TextRenderer textRenderer;
    private final HudTextManager textManager;

    TextHudRenderer() {
        this.textRenderer = MinecraftClient.getInstance().textRenderer;
        this.textManager = new HudTextManager();
    }

    public final HudTextManager getTextManager() {
        return textManager;
    }

    public final boolean shouldRender() {
        // Only render the text-HUD if the HUD module is enabled and the f3 menu is not enabled.
        return Palladium.getInstance().getModuleManager().isModuleEnabled(PalladiumHudModule.class)
                && !MinecraftClient.getInstance().options.debugEnabled
                && !Palladium.getInstance().getGuiRenderer().getClickGUI().isOpen();
    }

    private void drawText(MatrixStack matrices, @NotNull ColorText text, int x, int y) {
        int x1 = x - 1;
        int y1 = y - 1;
        int x2 = x1 + textRenderer.getWidth(text.getString()) + 2;
        int y2 = y1 + textRenderer.fontHeight;

        DrawableHelper.fill(matrices, x1, y1, x2, y2, text.getBackgroundColor());
        textRenderer.draw(matrices, text.getString(), x, y, text.getTextColor());
    }

    private void drawTopLeftList(MatrixStack matrices) {

        if (textManager.getTopLeftList().isPresent()) {
            ArrayList<ColorText> list = textManager.getTopLeftList().get().getAll();

            int listSize = list.size();

            // Used to keep track of the last line of text's width, in order to draw a border
            // between the current line and the previous one.
            int lastWidth = 0;

            // Used to store the y-position of the final line of text being drawn, in order
            // to draw the border around that final line of text after the loop is complete.
            int finalYPos = 0;

            for (int i = 0; i < listSize; i++) {
                ColorText text = list.get(i);

                int x = 1;
                int y = 1 + ((textRenderer.fontHeight) * i);
                int width = textRenderer.getWidth(text.getString());

                // Draw

                // Draw the text itself.
                drawText(matrices, text, x, y);

                // Draw the vertical line at the end of the text.
                DrawHelper.drawVerticalLine(matrices, width + 2, y - 1, y + textRenderer.fontHeight - 1, Colors.LAVENDER);

                /*
                 Draw the horizontal line connecting this text's vertical line with the last one's,
                 thereby forming an outline of the text. The horizontal line is not to be done on
                 the first iteration, as there is no previous line to connect it with.
                */
                if (i > 0) {
                    /*
                    Determine the direction this horizontal line needs to go, left or right.
                    If it is going left, then the horizontal line needs to be drawn one pixel
                    higher, since it will be above the current text-box. If it is going right,
                    then it needs to be drawn one pixel lower, since it will be below the
                    preceding text-box.
                     */

                    // Going to the left
                    if (width > lastWidth) {
                        DrawHelper.drawHorizontalLine(matrices, width + 3, lastWidth + 3, y - 2, Colors.LAVENDER);
                    }

                    // Going to the right
                    else if (width < lastWidth) {
                        DrawHelper.drawHorizontalLine(matrices, width + 3, lastWidth + 3, y - 1, Colors.LAVENDER);
                    }
                }

                // Update the last-width variable.
                lastWidth = width;
                finalYPos = y;
            }

            // Draw the final horizontal line after the loop is complete, if there was anything in the list.
            if (listSize > 0) {
                DrawHelper.drawHorizontalLine(matrices, 0, lastWidth + 3, finalYPos + textRenderer.fontHeight - 1, Colors.LAVENDER);
            }
        }
    }

    private void drawTopRightList(MatrixStack matrices) {
        if (textManager.getTopRightList().isPresent()) {
            ArrayList<ColorText> list = textManager.getTopRightList().get().getAll();

            int listSize = list.size();
            int lastWidth = 0;
            int finalXPos = 0;
            int finalYPos = 0;

            for (int i = 0; i < listSize; i++) {
                ColorText text = list.get(i);

                int x = MinecraftClient.getInstance().getWindow().getScaledWidth() - textRenderer.getWidth(text.getString()) - 1;
                int y = 1 + (textRenderer.fontHeight * i);
                int width = textRenderer.getWidth(text.getString());

                drawText(matrices, text, x, y);
                DrawHelper.drawVerticalLine(matrices, x - 2, y - 1, y + textRenderer.fontHeight - 1, Colors.LAVENDER);

                if (i > 0) {
                    if (width > lastWidth) {
                        DrawHelper.drawHorizontalLine(matrices, x - 2, x + (width - lastWidth) - 2, y - 2, Colors.LAVENDER);
                    }
                    else if (width < lastWidth) {
                        DrawHelper.drawHorizontalLine(matrices, x - 2, x + (width - lastWidth) - 2, y - 1, Colors.LAVENDER);
                    }
                }

                lastWidth = width;
                finalXPos = x;
                finalYPos = y;
            }

            if (listSize > 0) {
                DrawHelper.drawHorizontalLine(matrices, finalXPos - 2, finalXPos + lastWidth + 1, finalYPos + textRenderer.fontHeight - 1, Colors.LAVENDER);
            }
        }
    }

    private void drawBottomRightList(MatrixStack matrices) {
        if (textManager.getBottomRightList().isPresent()) {
            ArrayList<ColorText> list = textManager.getBottomRightList().get().getBody();

            int listSize = list.size();
            int lastWidth = 0;
            int finalXPos = 0;
            int finalYPos = 0;

            for (int i = 0; i < listSize; i++) {
                ColorText text = list.get(i);

                int width = textRenderer.getWidth(text.getString());
                int x = MinecraftClient.getInstance().getWindow().getScaledWidth() - textRenderer.getWidth(text.getString()) - 1;
                int y = (MinecraftClient.getInstance().getWindow().getScaledHeight() - textRenderer.fontHeight) - (textRenderer.fontHeight * i);

                drawText(matrices, text, x, y);
                DrawHelper.drawVerticalLine(matrices, x - 2, y - 1, y + textRenderer.fontHeight - 1, Colors.LAVENDER);

                if (i > 0) {
                    // Going right
                    if (width > lastWidth) {
                        DrawHelper.drawHorizontalLine(matrices, x - 2, x + (width - lastWidth) - 2, y + textRenderer.fontHeight - 1, Colors.LAVENDER);
                    }
                    // Going left
                    else if (width < lastWidth) {
                        DrawHelper.drawHorizontalLine(matrices, x - 2, x + (width - lastWidth) - 2, y + textRenderer.fontHeight - 2, Colors.LAVENDER);
                    }
                }

                lastWidth = width;
                finalXPos = x;
                finalYPos = y;
            }

            if (listSize > 0) {
                DrawHelper.drawHorizontalLine(matrices, finalXPos - 2, finalXPos + lastWidth + 1, finalYPos - 2, Colors.LAVENDER);
            }
        }
    }

    // Master render method that uses all the other methods to draw everything.
    public void render(MatrixStack matrices) {
        drawTopLeftList(matrices);
        drawTopRightList(matrices);
        drawBottomRightList(matrices);
    }
}
