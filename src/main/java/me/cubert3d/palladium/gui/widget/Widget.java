package me.cubert3d.palladium.gui.widget;

import me.cubert3d.palladium.gui.ClickGUI;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.util.math.MatrixStack;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/20/2021",
        status = "in-progress"
)

public abstract class Widget {

    private final String id;
    private boolean focused;

    private int x;
    private int y;
    private int width;
    private int height;
    private int color;

    protected Widget(String id) {
        this.id = id;
        this.focused = false;
        WidgetManager.addWidget(this);
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof Widget) {
            return ((Widget) obj).getId().equals(id);
        }
        return false;
    }

    @Override
    public final String toString() {
        return id;
    }

    public final String getId() {
        return id;
    }

    public final boolean isFocused() {
        return focused;
    }

    protected final void makeFocused() {
        WidgetManager.setFocusedWidget(this);
    }

    final void setFocused(boolean focused) {
        this.focused = focused;
    }



    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public final void setX(int x) {
        this.x = x;
    }

    public final void setY(int y) {
        this.y = y;
    }



    public final int getWidth() {
        return width;
    }

    public final int getHeight() {
        return height;
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
        return ClickGUI.shouldRender();
    }

    public abstract void render(MatrixStack matrices);
}
