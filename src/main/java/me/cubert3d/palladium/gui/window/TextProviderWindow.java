package me.cubert3d.palladium.gui.window;

import me.cubert3d.palladium.gui.DrawHelper;
import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.gui.text.TextProvider;
import me.cubert3d.palladium.module.modules.gui.AbstractHudModule;
import me.cubert3d.palladium.util.Vector2X;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

@ClassInfo(
        authors = "cubert3d",
        date = "7/24/2021",
        type = ClassType.WIDGET
)

public final class TextProviderWindow extends CloseableWindow {

    private final TextProvider textProvider;
    private final AbstractHudModule module;

    private TextProviderWindow(String id, int x, int y, int width, int height, int color, TextProvider textProvider, AbstractHudModule module) {
        super(id, x, y, width, height, color);
        this.textProvider = textProvider;
        this.module = module;
    }

    @Override
    public final String getLabel() {
        return textProvider.getTitle().getString();
    }

    @Override
    public void open() {
        super.open();
        module.enable();
    }

    @Override
    public void close() {
        super.close();
        module.disable();
    }

    @Override
    public final void render(MatrixStack matrices) {
        if (!isMinimized()) {
            drawMainWindow(matrices);
            drawText(matrices);
        }
        else {
            drawMinimizedWindow(matrices);
        }
        drawWindowControls(matrices);
    }

    private void drawText(MatrixStack matrices) {
        int counter = 0;
        int size = textProvider.getBody().size();
        boolean isListTooBig = size > getListSpaceAvailable();
        for (ColorText text : textProvider.getBody()) {

            String string = text.getString();
            int color = isMouseOverLine(counter) ? Colors.HIGHLIGHT : Colors.WHITE;

            int x3 = getX() + 2;
            int y3 = getY() + DrawHelper.getTextHeight() + 1 + (DrawHelper.getTextHeight() * counter);

            /*
            Check if there is enough room to keep printing the list.
            If the number of available lines is the same or greater than the number
            of strings in the text-list, then just print the whole list. But if there
            is not enough room, that needs to be determined beforehand so that this can
            stop printing the list on the second-to-last line, so as to leave space for
            an addition line that says how many lines are not displayed. (...and x more)
             */
            if (!isListTooBig || counter < getListSpaceAvailable() - 1) {
                DrawHelper.drawText(matrices, string, x3, y3, getWindowWidth(), color);
            }
            else if (counter == getListSpaceAvailable() - 1) {
                int remainder = size - counter;
                String string2 = "...and " + remainder + " more";
                DrawHelper.drawText(matrices, string2, x3, y3, getWindowWidth(), color);
            }

            counter++;
        }
    }

    // Gets the number of lines available in the window for text.
    private int getListSpaceAvailable() {
        return getWindowHeight() / (DrawHelper.getTextHeight());
    }

    public static @NotNull TextProviderWindow newDisplayWindow(String id, TextProvider textProvider, AbstractHudModule module) {
        Vector2X<Integer> coordinate = getWindowManager().getNextWindowPosition();
        int x = coordinate.getX();
        int y = coordinate.getY();
        int width = DEFAULT_WIDTH;
        int height = DEFAULT_HEIGHT;
        int color = getWindowManager().getNextColor();
        return new TextProviderWindow(id, x, y, width, height, color, textProvider, module);
    }
}
