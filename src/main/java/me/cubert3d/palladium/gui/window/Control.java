package me.cubert3d.palladium.gui.window;

import me.cubert3d.palladium.gui.DrawHelper;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

@ClassInfo(
        authors = "cubert3d",
        date = "7/24/2021",
        type = ClassType.WIDGET
)

public abstract class Control {

    public static final int COLOR = Colors.LAVENDER;
    public static final int size = 7;

    protected final int position;
    protected final Window window;

    protected Control(int position, Window window) {
        this.position = position;
        this.window = window;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Control) {
            Control other = (Control) obj;
            return this.getType().equals(other.getType()) || this.position == other.position;
        }
        else {
            return false;
        }
    }

    public abstract Type getType();

    /**
     * <p>
     *     Checks whether the position of a mouse click falls on this control.
     * </p>
     * @param mouseX the x-position of the mouse upon click
     * @param mouseY the y-position of the mouse upon click
     * @return true if the mouse is on this control upon click, false if it is not
     */
    public final boolean isClicked(int mouseX, int mouseY) {
        int minX = window.getX2() - ((position + 1) * 7) - (position * 2) - 2;
        int minY = window.getY();
        int maxX = window.getX2() - (position * 7) - (position * 2);
        int maxY = window.getY() + 9;
        return mouseX >= minX && mouseX < maxX && mouseY >= minY && mouseY <= maxY;
    }

    public abstract void onClick();

    public abstract void draw(MatrixStack matrices);

    public static final class Pin extends Control {

        public Pin(int position, Window window) {
            super(position, window);
        }

        @Override
        public Type getType() {
            return Type.PIN;
        }

        @Override
        public void onClick() {
            window.togglePinned();
        }

        @Override
        public void draw(MatrixStack matrices) {
            int x1 = window.getX2() - ((position + 1) * 7) - (position * 2) - 1;
            int y1 = window.getY() + 1;
            int x2 = window.getX2() - (position * 7) - (position * 2) - 1;
            int y2 = window.getY() + DrawHelper.FONT_HEIGHT - 1;
            DrawHelper.drawOutlineBox(matrices, x1, y1, x2, y2, COLOR);

            if (window.isPinned()) {
                int innerX1 = x1 + 2;
                int innerY1 = y1 + 2;
                int innerX2 = x2 - 2;
                int innerY2 = y2 - 2;
                DrawHelper.drawBox(matrices, innerX1, innerY1, innerX2, innerY2, COLOR);
            }
        }
    }

    public static final class Close extends Control {

        public Close(int position, Window window) {
            super(position, window);
        }

        @Override
        public Type getType() {
            return Type.CLOSE;
        }

        @Override
        public void onClick() {
            window.close();
        }

        @Override
        public void draw(MatrixStack matrices) {
            int x1 = window.getX2() - ((position + 1) * 7) - (position * 2) - 1;
            int y1 = window.getY() + 1;
            int x2 = window.getX2() - (position * 7) - (position * 2) - 1;
            int y2 = window.getY() + DrawHelper.FONT_HEIGHT - 1;
            DrawHelper.drawCross(matrices, x1, y1, x2, y2, COLOR);
        }
    }

    public static final class Minimize extends Control {

        public Minimize(int position, Window window) {
            super(position, window);
        }

        @Override
        public Type getType() {
            return Type.MINIMIZE;
        }

        @Override
        public void onClick() {
            window.toggleMinimized();
        }

        @Override
        public void draw(MatrixStack matrices) {
            int x1 = window.getX2() - ((position + 1) * 7) - (position * 2) - 1;
            int y1 = window.getY() + DrawHelper.FONT_HEIGHT - 2;
            int x2 = window.getX2() - (position * 7) - (position * 2) - 1;
            int y2 = window.getY() + DrawHelper.FONT_HEIGHT - 1;
            DrawableHelper.fill(matrices, x1, y1, x2, y2, COLOR);
        }
    }

    public enum Type {
        PIN,
        CLOSE,
        MINIMIZE
    }
}
