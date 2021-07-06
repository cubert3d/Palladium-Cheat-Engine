package me.cubert3d.palladium.gui.widget;

import me.cubert3d.palladium.util.Common;
import me.cubert3d.palladium.gui.ClickGUI;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.util.math.MatrixStack;

import java.util.HashSet;
import java.util.Set;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/20/2021",
        status = "in-progress"
)

public final class WidgetManager {

    private final Set<Widget> widgets;
    private final ClickGUI clickGUI;

    /*
     This holds the widget that is currently being clicked, so that the onDrag()
     method does not have to iterate through the set of widgets every single time
     the mouse is moved.
    */
    private Widget clickedWidget;

    public WidgetManager(ClickGUI clickGUI) {
        this.widgets = new HashSet<>();
        this.clickGUI = clickGUI;
    }

    public Set<Widget> getWidgets() {
        return widgets;
    }

    void addWidget(Widget widget) {
        widgets.add(widget);
    }

    public int getScaledWidth() {
        return Common.getMC().getWindow().getScaledWidth();
    }

    public int getScaledHeight() {
        return Common.getMC().getWindow().getScaledHeight();
    }

    public void render(MatrixStack matrices) {
        for (Widget widget : widgets) {
            widget.render(matrices);
        }
    }

    public void onClick(int mousePosX, int mousePosY, boolean isRelease) {

        // Nothing should be clicked if there isn't anything to be clicked.
        if (!clickGUI.shouldRender()) {
            return;
        }

        for (Widget widget : widgets) {
            if (mousePosX >= widget.getX() && mousePosX <= widget.getX2()
                    && mousePosY >= widget.getY() && mousePosY <= widget.getY2()) {

                if (!isRelease)
                    clickedWidget = widget;
                else
                    clickedWidget = null;

                widget.onClick(mousePosX, mousePosY, isRelease);
                break;
            }
        }
    }

    public void onMouseMove(int mousePosX, int mousePosY) {
        if (clickedWidget != null) {
            clickedWidget.onDrag(mousePosX, mousePosY);
        }
    }

    // This is called when the ClickGUI is closed.
    public void resetClickedWidget() {
        clickedWidget = null;
    }
}
