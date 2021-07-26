package me.cubert3d.palladium.gui.window;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.gui.ClickGUI;
import me.cubert3d.palladium.gui.DrawHelper;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.util.LinkedHashSet;
import java.util.Set;

@ClassInfo(
        authors = "cubert3d",
        date = "7/24/2021",
        type = ClassType.WINDOW
)

public abstract class Window {

    public static final int DEFAULT_WIDTH = 120;
    public static final int DEFAULT_HEIGHT = 90;
    public static final int DEFAULT_X = 200;
    public static final int DEFAULT_Y = 25;
    public static int BORDER_COLOR = Colors.BACKGROUND_GRAY;

    private final String id;
    private boolean pinned;
    private boolean minimized;
    private boolean focused;
    private int x;
    private int y;
    private int width;
    private int height;
    private int color;
    protected final Set<Control> controls;
    protected final WindowManager windowManager;

    protected int cursorDistanceX = 0;
    protected int cursorDistanceY = 0;

    protected Window(String id, int x, int y, int width, int height, int color) {
        this.id = id;
        this.pinned = false;
        this.minimized = false;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.controls = buildControls();
        this.windowManager = getWindowManager();
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof Window) {
            return this.getID().equals(((Window) obj).getID());
        }
        return false;
    }

    /**
     * <p>
     *     Returns the ID of this window, which is a String, and which should be unique
     * </p>
     * @return the ID of this window
     */
    public final String getID() {
        return id;
    }

    public String getLabel() {
        return id;
    }

    /**
     * <p>
     *     Determines the color with which the label will be printed. If this window is focused,
     *     then the label will be white; if not, then the label will be lavender.
     * </p>
     *
     * @return the color of the label
     */
    public final int getLabelColor() {
        return focused ? Colors.WHITE : Colors.LAVENDER;
    }

    /**
     * <p>
     *     Determines the color with which the borders of this window will be drawn. If this window is focused,
     *     then the label will be a bright white; if not, then the label will be a gray, or off-white.
     * </p>
     *
     * @return the color of the label
     */
    public final int getBorderColor() {
        return focused ? Colors.BACKGROUND_WHITE : Colors.BACKGROUND_GRAY;
    }

    public boolean isOpen() {
        return true;
    }

    public void open() {

    }

    public void close() {

    }

    public final void focus() {
        this.windowManager.focusWindow(this);
        this.focused = true;
    }

    public final void unfocus() {
        this.focused = false;
    }

    public final boolean isPinned() {
        return pinned;
    }

    public final void togglePinned() {
        this.pinned = !this.pinned;
    }

    public final boolean isMinimized() {
        return minimized;
    }

    public final void toggleMinimized() {
        this.minimized = !this.minimized;
    }

    public final int getX() {
        return x;
    }

    public final int getX2() {
        return getX() + getWidth();
    }

    public final void setX(int x) {
        this.x = x;
    }

    public final int getY() {
        return y;
    }

    public final int getY2() {
        return getY() + getHeight();
    }

    public final void setY(int y) {
        this.y = y;
    }

    /**
     * <p>
     *     Returns the width of this window.
     * </p>
     *
     * @return the width of this window, in pixels.
     */
    public final int getWidth() {
        return width;
    }

    public final void setWidth(int width) {
        this.width = width;
    }

    /**
     * <p>
     *     Returns the height of this window.
     * </p>
     *
     * @return the height of this window, in pixels.
     */
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Returns the width of the box inside the borders, underneath the label.
     *
     * @return the width of inner box, in pixels
     */
    protected final int getWindowWidth() {
        return getWidth() - 2;
    }

    /**
     * Returns the height of the box inside the borders, underneath the label.
     *
     * @return the height of the inner box, in pixels
     */
    protected final int getWindowHeight() {
        return getHeight() - DrawHelper.getTextHeight() - 1;
    }

    /**
     * <p>
     *     Returns the color of this window, which is usually used as the color of the window body itself.
     * </p>
     *
     * @return the color of this window
     */
    public final int getColor() {
        return color;
    }

    /**
     * <p>
     *     Sets the color of this window, which is usually used as the color of the window body itself.
     * </p>
     *
     * @param color the new color to be applied to this window
     */
    public final void setColor(int color) {
        this.color = color;
    }

    /**
     * <p>
     *     Builds a set of controls for this window. This method can be overriden to give a window a different set of controls.
     * </p>
     *
     * @return the set of controls that this window will have
     */
    protected Set<Control> buildControls() {
        Set<Control> controls = new LinkedHashSet<>();
        controls.add(new Control.Pin(0, this));
        controls.add(new Control.Minimize(1, this));
        return controls;
    }

    /**
     * <p>
     *     Checks whether this window ought to be render.
     * </p>
     * <p>
     *     This window should only be rendered if either the click-GUI is currently open, or if this window is currently pinned.
     *     In addition to this, this method checks if this window is actually open, even though this window should not be processed
     *     for rendering if it is closed in the first place.
     * </p>
     *
     * @param clickGUI Palladium's click-GUI, to be checked if it is currently open
     *
     * @return {@code true} if this window should rendered, {@code false} if it should not
     */
    public final boolean shouldRender(ClickGUI clickGUI) {
        return isOpen() && (clickGUI.shouldRender() || isPinned());
    }

    /**
     * <p>
     *     Renders this window.
     * </p>
     *
     * @param matrices matrix-stack with which to render this window
     * @param mouseX the x-position of the mouse
     * @param mouseY the y-position of the mouse
     */
    public void render(MatrixStack matrices, int mouseX, int mouseY) {
        if (!isMinimized()) {
            drawMainWindow(matrices);
        }
        else {
            drawMinimizedWindow(matrices);
        }
        drawWindowControls(matrices);
    }

    protected final void drawMainWindow(MatrixStack matrices) {

        // Toolbar
        DrawableHelper.fill(matrices, getX(), getY(), getX2(), getY() + DrawHelper.getTextHeight(), getBorderColor());

        // Label
        DrawHelper.drawText(matrices, getLabel(), getX() + 1, getY() + 1, getWidth() - 2 - 8 - 9, getLabelColor());

        // Main box
        DrawableHelper.fill(matrices, getX() + 1, getY() + DrawHelper.getTextHeight(), getX2() - 1, getY2() - 1, getColor());

        // Right Border
        DrawableHelper.fill(matrices, getX2() - 1, getY() + DrawHelper.getTextHeight(), getX2(), getY2() - 1, getBorderColor());

        // Bottom Border
        DrawableHelper.fill(matrices, getX(), getY2() - 1, getX2(), getY2(), getBorderColor());

        // Left Border
        DrawableHelper.fill(matrices, getX(), getY() + DrawHelper.getTextHeight(), getX() + 1, getY2() - 1, getBorderColor());
    }

    protected final void drawMinimizedWindow(MatrixStack matrices) {

        // Toolbar
        DrawableHelper.fill(matrices, getX(), getY(), getX2(), getY() + DrawHelper.getTextHeight(), getBorderColor());

        // Label
        DrawHelper.drawText(matrices, getLabel(), getX() + 1, getY() + 1, getWidth() - 2 - 8 - 9, getLabelColor());
    }

    protected final void drawWindowControls(MatrixStack matrices) {
        controls.forEach(control -> control.draw(matrices));
    }

    /**
     * <p>
     *     Checks whether the mouse is hovering over this window.
     * </p>
     * @param mouseX the x-position of the mouse
     * @param mouseY the y-position of the mouse
     * @return {@code true} if the mouse position is inside the window, {@code false} if it is not
     */
    public boolean isMouseOverThis(int mouseX, int mouseY) {
        return mouseX >= getX() && mouseX <= getX2() && mouseY >= getY() && mouseY <= getY2();
    }

    protected final void onClick(int mouseX, int mouseY, boolean isRelease) {
        this.focus();

        controls.forEach(control -> {
            if (control.isClicked(mouseX, mouseY) && !isRelease) {
                control.onClick();
            }
        });

        if (mouseY <= getY() + DrawHelper.FONT_HEIGHT) {
            if (!isRelease) {
                cursorDistanceX = mouseX - getX();
                cursorDistanceY = mouseY - getY();
                windowManager.setDraggedWindow(this);
            }
        }
        else {
            onClickWindow(mouseX, mouseY, isRelease);
        }
    }

    protected void onClickWindow(int mouseX, int mouseY, boolean isRelease) {

    }

    protected final void drag(int mouseX, int mouseY) {
        this.setX(mouseX - cursorDistanceX);
        this.setY(mouseY - cursorDistanceY);
    }

    protected static WindowManager getWindowManager() {
        return Palladium.getInstance().getGuiRenderer().getClickGUI().getWindowManager();
    }
}
