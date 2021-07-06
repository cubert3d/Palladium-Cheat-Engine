package me.cubert3d.palladium.gui.widget;

import me.cubert3d.palladium.gui.DrawHelper;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/20/2021",
        status = "in-progress"
)

public class Window extends Widget {

    public static int BORDER_COLOR = Colors.BACKGROUND_WHITE;
    public static int LABEL_COLOR = Colors.LAVENDER;

    // The string that is displayed at the top of the window.
    private final String label;
    protected boolean pinned = false;
    protected boolean minimized = false;

    /*
    These are used to store the distance between the mouse cursor and the origin point
    of this widget, when this widget's toolbar is clicked. With these, the position of
    this widget, when it is being dragged, will update corresponding to its original
    position relative to the position of the mouse cursor when it was clicked.
     */
    protected int cursorDistanceX = 0;
    protected int cursorDistanceY = 0;

    public Window(String id, String label, WidgetManager widgetManager) {
        super(id, widgetManager);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    // Returns the width of the box inside the borders, underneath the label.
    protected final int getWindowWidth() {
        return getScaledWidth() - 2;
    }

    // Returns the height of the box inside the borders, underneath the label.
    protected final int getWindowHeight() {
        return getScaledHeight() - DrawHelper.getTextHeight() - 1;
    }

    // Gets the number of lines available in the window for text.
    protected final int getListSpaceAvailable() {
        return getWindowHeight() / (DrawHelper.getTextHeight());
    }



    @Override
    public boolean shouldRender() {
        return super.shouldRender() || pinned;
    }

    @Override
    public void render(MatrixStack matrices) {
        if (!minimized) {
            drawMainWindow(matrices);
        }
        else {
            drawMinimizedWindow(matrices);
        }
        drawWindowControls(matrices);
    }

    protected final void drawMainWindow(MatrixStack matrices) {
        /*
        Renders the main window itself, with the top bar and the borders.
         */

        // Toolbar
        DrawableHelper.fill(matrices, getX(), getY(), getX2(), getY() + DrawHelper.getTextHeight(), BORDER_COLOR);

        // Label
        DrawHelper.drawText(matrices, getLabel(), getX() + 1, getY() + 1, getScaledWidth() - 2 - 8 - 9, DrawHelper.getTextHeight(), LABEL_COLOR);

        // Main box
        DrawableHelper.fill(matrices, getX() + 1, getY() + DrawHelper.getTextHeight(), getX2() - 1, getY2() - 1, getColor());

        // Right Border
        DrawableHelper.fill(matrices, getX2() - 1, getY() + DrawHelper.getTextHeight(), getX2(), getY2() - 1, BORDER_COLOR);

        // Bottom Border
        DrawableHelper.fill(matrices, getX(), getY2() - 1, getX2(), getY2(), BORDER_COLOR);

        // Left Border
        DrawableHelper.fill(matrices, getX(), getY() + DrawHelper.getTextHeight(), getX() + 1, getY2() - 1, BORDER_COLOR);
    }

    protected final void drawMinimizedWindow(MatrixStack matrices) {

        // Toolbar
        DrawableHelper.fill(matrices, getX(), getY(), getX2(), getY() + DrawHelper.getTextHeight(), BORDER_COLOR);

        // Label
        DrawHelper.drawText(matrices, getLabel(), getX() + 1, getY() + 1, getScaledWidth() - 2 - 8 - 9, DrawHelper.getTextHeight(), LABEL_COLOR);
    }

    protected void drawWindowControls(MatrixStack matrices) {
        /*
        Renders the controls at the top of the window.
         */

        // Minimize control
        DrawableHelper.fill(matrices, getX2() - 8, getY() + DrawHelper.getTextHeight() - 2, getX2() - 1, getY() + DrawHelper.getTextHeight() - 1, LABEL_COLOR);

        // Pin control
        DrawHelper.drawOutlineBox(matrices, getX2() - 10 - 6 - 1, getY() + 1, getX2() - 9 - 1, getY() + DrawHelper.getTextHeight() - 1, LABEL_COLOR);

        if (pinned) {
            DrawHelper.drawBox(matrices, getX2() - 10 - 6 - 1 + 2, getY() + 1 + 2, getX2() - 9 - 2 - 1, getY() + DrawHelper.getTextHeight() - 1 - 2, LABEL_COLOR);
        }
    }



    @Override
    protected void onClick(int mousePosX, int mousePosY, boolean isRelease) {

        ClickLocation location = getClickLocation(mousePosX, mousePosY);

        switch (location) {
            case TOOLBAR:
                cursorDistanceX = mousePosX - getX();
                cursorDistanceY = mousePosY - getY();
                break;

            case MINIMIZE_CONTROL:
                if (!isRelease)
                    minimized = !minimized;
                break;

            case PIN_CONTROL:
                if (!isRelease)
                    pinned = !pinned;
                break;
        }
    }

    @Override
    protected void onDrag(int mousePosX, int mousePosY) {
        this.setX(mousePosX - cursorDistanceX);
        this.setY(mousePosY - cursorDistanceY);
    }

    protected ClickLocation getClickLocation(int mousePosX, int mousePosY) {
        if (mousePosX >= getX() && mousePosX < getX2() - 18
                && mousePosY >= getY() && mousePosY <= getY() + 9) {
            return ClickLocation.TOOLBAR;
        }
        else if (mousePosX >= getX2() - 18 && mousePosX < getX2() - 9
                && mousePosY >= getY() && mousePosY <= getY() + 9) {
            return ClickLocation.PIN_CONTROL;
        }
        else if (mousePosX >= getX2() - 9 && mousePosX <= getX2()
                && mousePosY >= getY() && mousePosY <= getY() + 9) {
            return ClickLocation.MINIMIZE_CONTROL;
        }
        return ClickLocation.WINDOW;
    }

    protected enum ClickLocation {
        TOOLBAR,
        CLOSE_CONTROL,
        MINIMIZE_CONTROL,
        PIN_CONTROL,
        WINDOW
    }
}
