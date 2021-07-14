package me.cubert3d.palladium.gui.widget;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.util.math.MatrixStack;

@ClassInfo(
        authors = "cubert3d",
        date = "4/20/2021",
        type = ClassType.WIDGET,
        complete = false
)

public abstract class Widget {

    // Unique identifier for this widget, in the form a string.
    private final String id;

    // Position of this widget on the screen.
    private int x;
    private int y;

    // Dimensions of this widget.
    private int width;
    private int height;

    // Special color of this widget.
    private int color;

    protected Widget(String id, WidgetManager widgetManager) {
        this.id = id;
        widgetManager.addWidget(this);
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof Widget) {
            return ((Widget) obj).getID().equals(id);
        }
        return false;
    }

    @Override
    public final String toString() {
        return id;
    }

    public final String getID() {
        return id;
    }



    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public final int getX2() {
        return x + getScaledWidth();
    }

    public final int getY2() {
        return y + getScaledHeight();
    }

    public final void setX(int x) {
        this.x = x;
    }

    public final void setY(int y) {
        this.y = y;
    }



    public final int getScaledWidth() {
        return width;
    }

    public final int getScaledHeight() {
        return height;
    }

    public final void setWidth(int width) {
        this.width = width;
    }

    public final void setHeight(int height) {
        this.height = height;
    }



    public final int getColor() {
        return color;
    }

    public final void setColor(int color) {
        this.color = color;
    }



    /*
    Whether or not this widget should render.
    By default, just returns the ClickGUI should-render state;
    but other kinds of widgets might have special conditions...
     */
    public boolean shouldRender() {
        return Palladium.getInstance().getGuiRenderer().getClickGUI().shouldRender();
    }

    /*
    Renders this widget on the screen.
     */
    public abstract void render(MatrixStack matrices);



    /*
    This method is called when this widget is clicked on.
     */
    protected void onClick(int mousePosX, int mousePosY, boolean isRelease) {

    }

    protected void onDrag(int mousePosX, int mousePosY) {

    }
}
