package me.cubert3d.palladium.gui.window;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.gui.ClickGUI;
import me.cubert3d.palladium.gui.DrawHelper;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.util.Vector2X;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.util.LinkedHashSet;
import java.util.Set;

@ClassInfo(
        authors = "cubert3d",
        date = "7/24/2021",
        type = ClassType.WIDGET
)

public abstract class Window {

    public static final int DEFAULT_WIDTH = 120;
    public static final int DEFAULT_HEIGHT = 90;
    public static final int DEFAULT_X = 200;
    public static final int DEFAULT_Y = 25;
    public static int BORDER_COLOR = Colors.BACKGROUND_WHITE;
    public static int LABEL_COLOR = Colors.LAVENDER;

    private final String id;
    private boolean pinned;
    private boolean minimized;
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

    public boolean isOpen() {
        return true;
    }

    public void open() {

    }

    public void close() {

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
     * @return the width of inner box, in pixels
     */
    protected final int getWindowWidth() {
        return getWidth() - 2;
    }

    /**
     * Returns the height of the box inside the borders, underneath the label.
     * @return the height of the inner box, in pixels
     */
    protected final int getWindowHeight() {
        return getHeight() - DrawHelper.getTextHeight() - 1;
    }

    /**
     * <p>
     *     Returns the color of this window, which is usually used as the color of the window body itself.
     * </p>
     * @return the color of this window
     */
    public final int getColor() {
        return color;
    }

    /**
     * <p>
     *     Sets the color of this window, which is usually used as the color of the window body itself.
     * </p>
     * @param color the new color to be applied to this window
     */
    public final void setColor(int color) {
        this.color = color;
    }

    /**
     * <p>
     *     Builds a set of controls for this window. This method can be overriden to give a window a different set of controls.
     * </p>
     * @return the set of controls that this window will have
     */
    protected Set<Control> buildControls() {
        Set<Control> controls = new LinkedHashSet<>();
        controls.add(new Control.Pin(0, this));
        controls.add(new Control.Minimize(1, this));
        return controls;
    }



    public final boolean shouldRender(ClickGUI clickGUI) {
        return isOpen() && (clickGUI.shouldRender() || isPinned());
    }

    /**
     * <p>
     *     Renders this window.
     * </p>
     * @param matrices matrix-stack with which to render this window
     */
    public void render(MatrixStack matrices) {
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
        DrawableHelper.fill(matrices, getX(), getY(), getX2(), getY() + DrawHelper.getTextHeight(), BORDER_COLOR);

        // Label
        DrawHelper.drawText(matrices, getLabel(), getX() + 1, getY() + 1, getWidth() - 2 - 8 - 9, LABEL_COLOR);

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
        DrawHelper.drawText(matrices, getLabel(), getX() + 1, getY() + 1, getWidth() - 2 - 8 - 9, LABEL_COLOR);
    }

    protected final void drawWindowControls(MatrixStack matrices) {
        controls.forEach(control -> control.draw(matrices));
    }



    public boolean isClicked(int mouseX, int mouseY) {
        return mouseX >= getX() && mouseX <= getX2() && mouseY >= getY() && mouseY <= getY2();
    }

    protected final void onClick(int mouseX, int mouseY, boolean isRelease) {
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

    // Returns the index of the line that was clicked, or -1 if none were clicked.
    protected final int getLineMouseOver(int mouseX, int mouseY) {
        if (mouseX > getX() && mouseX < getX2() && mouseY > getY() + DrawHelper.FONT_HEIGHT && mouseY < getY2()) {
            /*
            Subtract the Y-mouse-position by the Y-position of this window, so that
            it is relative to the position of this window. Then subtract 9 to account
            for the toolbar. Finally, divide by 9, the height of a line of text, to
            determine which line of text--that is, module--was clicked.
             */
            return (mouseY - getY() - 9) / 9;
        }
        else {
            return -1;
        }
    }

    protected final boolean isMouseOverLine(int index) {
        Mouse mouse = MinecraftClient.getInstance().mouse;
        Vector2X<Integer> position = windowManager.scaleMousePosition(mouse.getX(), mouse.getY());
        int lineIndex = getLineMouseOver(position.getX(), position.getY());
        return index == lineIndex;
    }

    protected static WindowManager getWindowManager() {
        return Palladium.getInstance().getGuiRenderer().getClickGUI().getWindowManager();
    }
}
