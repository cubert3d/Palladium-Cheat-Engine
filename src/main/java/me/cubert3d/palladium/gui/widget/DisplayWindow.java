package me.cubert3d.palladium.gui.widget;

import me.cubert3d.palladium.gui.DrawHelper;
import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.gui.text.provider.TextProvider;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.function.Supplier;

@ClassInfo(
        authors = "cubert3d",
        date = "4/23/2021",
        type = ClassType.WIDGET
)

/*
This is a window that simply displays information, in the form of text.
 */

public final class DisplayWindow extends Window {

    private static final Supplier<ArrayList<ColorText>> emptySupplier = ArrayList::new;

    private Supplier<ArrayList<ColorText>> textSupplier;

    private TextProvider textProvider;

    public DisplayWindow(String id, WidgetManager widgetManager) {
        super(id, "Window", widgetManager);
        this.textSupplier = emptySupplier;
    }

    public DisplayWindow(String id, String label, TextProvider textProvider, WidgetManager widgetManager) {
        super(id, label, widgetManager);
        this.textProvider = textProvider;
    }

    @Override
    public final String getLabel() {
        return textProvider.getHeader().getString();
    }

    public void setTextProvider(TextProvider textProvider) {
        this.textProvider = textProvider;
    }

    @Override
    public void render(MatrixStack matrices) {
        if (!minimized) {
            drawMainWindow(matrices);
            drawTextInWindow(matrices);
        }
        else {
            drawMinimizedWindow(matrices);
        }
        drawWindowControls(matrices);
    }

    private void drawTextInWindow(MatrixStack matrices) {
        int counter = 0;
        int size = textSupplier.get().size();
        boolean isListTooBig = size > getListSpaceAvailable();
        for (ColorText text : textProvider.getBody()) {

            String string = text.getString();

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
                DrawHelper.drawText(matrices, string, x3, y3, getWindowWidth(), DrawHelper.getTextHeight(), Colors.WHITE);
            }
            else if (counter == getListSpaceAvailable() - 1) {
                int remainder = size - counter;
                String string2 = "...and " + remainder + " more";
                DrawHelper.drawText(matrices, string2, x3, y3, getWindowWidth(), DrawHelper.getTextHeight(), Colors.WHITE);
            }

            counter++;
        }
    }
}
