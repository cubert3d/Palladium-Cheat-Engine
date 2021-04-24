package me.cubert3d.palladium.gui.widget;

import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.gui.text.TextList;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.function.Supplier;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/23/2021",
        status = "in-progress"
)

/*
This is a window that simply displays information, in the form of text.
 */

public final class DisplayWindowWidget extends WindowWidget {

    private static final Supplier<ArrayList<ColorText>> emptySupplier = ArrayList::new;

    private Supplier<ArrayList<ColorText>> textSupplier;

    private TextList textList;

    public DisplayWindowWidget(String id, String label) {
        super(id, label);
        this.textSupplier = emptySupplier;
    }

    public DisplayWindowWidget(String id, String label, TextList textList) {
        super(id, label);
        this.textList = textList;
    }

    @Override
    public final String getLabel() {
        return textList.getHeader().getString();
    }

    public void setTextList(TextList textList) {
        this.textList = textList;
    }

    // Gets the number of lines available in the window for text.
    protected final int getListSpaceAvailable() {
        return getWindowHeight() / (DrawHelper.getTextHeight());
    }

    @Override
    public void render(MatrixStack matrices) {

        super.render(matrices);

        // Text
        int counter = 0;
        int size = textSupplier.get().size();
        boolean isListTooBig = size > getListSpaceAvailable();
        for (ColorText text : textList.getBody()) {

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
