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

import java.util.ArrayList;
import java.util.stream.Collectors;

@ClassInfo(
        authors = "cubert3d",
        date = "7/24/2021",
        type = ClassType.WINDOW
)

public final class TextProviderWindow extends CloseableWindow implements Displayable {

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
    public final void open() {
        super.open();
        module.enable();
    }

    @Override
    public final void close() {
        super.close();
        module.disable();
    }

    @Override
    public final void render(MatrixStack matrices, int mouseX, int mouseY) {
        if (!isMinimized()) {
            drawMainWindow(matrices);
            drawText(matrices, mouseX, mouseY);
        }
        else {
            drawMinimizedWindow(matrices);
        }
        drawWindowControls(matrices);
    }

    private void drawText(MatrixStack matrices, int mouseX, int mouseY) {
        int counter = 0;
        int size = textProvider.getBody().size();
        boolean isListTooBig = size > getListSpaceAvailable(this);
        for (String line : getText()) {

            boolean isMouseOverHigherWindow = windowManager.isMouseOverHigherWindow(mouseX, mouseY, this);
            int color = isMouseOverLine(counter, windowManager, this)
                    && !windowManager.hasDraggedWindow()
                    && !isMouseOverHigherWindow ? Colors.HIGHLIGHT : Colors.WHITE;

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
            if (!isListTooBig || counter < getListSpaceAvailable(this) - 1) {
                DrawHelper.drawText(matrices, line, x3, y3, getWindowWidth(), color);
            }
            else if (counter == getListSpaceAvailable(this) - 1) {
                int remainder = size - counter;
                String string2 = "...and " + remainder + " more";
                DrawHelper.drawText(matrices, string2, x3, y3, getWindowWidth(), color);
            }

            counter++;
        }
    }

    @Override
    public ArrayList<String> getText() {
        return textProvider.getBody().stream().map(ColorText::getString).collect(Collectors.toCollection(ArrayList::new));
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
