package me.cubert3d.palladium.gui.window;

import me.cubert3d.palladium.gui.ClickGUI;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.util.Vector2X;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Random;

@ClassInfo(
        authors = "cubert3d",
        date = "7/24/2021",
        type = ClassType.MANAGER
)

public final class WindowManager {

    private final ArrayDeque<Window> windows;
    private final Random random;
    private Window focusedWindow;
    private Window draggedWindow;
    private final ClickGUI clickGUI;
    private int lastMouseX;
    private int lastMouseY;

    public WindowManager(ClickGUI clickGUI) {
        this.windows = new ArrayDeque<>(1);
        this.random = new Random();
        this.clickGUI = clickGUI;
    }

    public final ArrayDeque<Window> getWindows() {
        return windows;
    }

    public void openWindow(Window window) {
        this.windows.add(window);
    }

    public void closeWindow(Window window) {
        this.windows.remove(window);
    }

    public void focusWindow(Window window) {
        if (this.focusedWindow != null) {
            this.focusedWindow.unfocus();
        }
        this.focusedWindow = window;
        if (this.windows.remove(window)) {
            this.windows.addLast(window);
        }
    }

    public final boolean hasDraggedWindow() {
        return draggedWindow != null;
    }

    public final void setDraggedWindow(Window draggedWindow) {
        this.draggedWindow = draggedWindow;
    }

    public final void resetDraggedWindow() {
        this.draggedWindow = null;
    }

    public final void updateMousePosition(int mouseX, int mouseY) {
        this.lastMouseX = mouseX;
        this.lastMouseY = mouseY;
    }

    private boolean isClickable() {
        return clickGUI.shouldRender();
    }

    // Called when the screen is clicked; checks each of the windows to see if the mouse was on it.
    public final void onClick(int mouseX, int mouseY, boolean isRelease) {
        if (isClickable()) {
            // Iterator is used because the window might be closed (ie removed from the set) during the loop
            Window nextWindow;
            Iterator<Window> iterator = windows.descendingIterator();
            while (iterator.hasNext()) {
                nextWindow = iterator.next();
                if (nextWindow.isMouseOverThis(mouseX, mouseY)) {
                    nextWindow.onClick(mouseX, mouseY, isRelease);
                    break;
                }
            }
            if (isRelease && draggedWindow != null) {
                resetDraggedWindow();
            }
        }
    }

    public final void onMouseMove(int mouseX, int mouseY) {
        updateMousePosition(mouseX, mouseY);
        if (draggedWindow != null) {
            draggedWindow.drag(mouseX, mouseY);
        }
    }

    /**
     * <p>
     *     Checks whether the mouse is over another window that is "above" the given window.
     * </p>
     * @param mouseX the x-position of the mouse
     * @param mouseY the y-position of the mouse
     * @param window the window to check against
     * @return {@code true} if the mouse position is hovering over a higher window, {@code false} if it is not
     */
    public final boolean isMouseOverHigherWindow(int mouseX, int mouseY, Window window) {
        for (Iterator<Window> iterator = windows.descendingIterator(); iterator.hasNext(); ) {
            Window other = iterator.next();
            if (other.equals(window)) {
                break;
            }
            if (other.isMouseOverThis(mouseX, mouseY)) {
                return true;
            }
        }
        return false;
    }

    public final void renderWindows(MatrixStack matrices) {
        for (Window window : windows) {
            if (window.shouldRender(clickGUI)) {
                window.render(matrices, lastMouseX, lastMouseY);
            }
        }
    }

    /**
     * <p>
     *     Used when a new window is being created, to check if there is already a window at or next to the default location of new windows.
     * </p>
     * <p>
     *     If there already is a window in the default location, then this method will return a location underneath and to the left of it.
     * </p>
     *
     * @return the position at which to open the next window
     */
    public @NotNull Vector2X<Integer> getNextWindowPosition() {
        for (Window window : windows) {
            int minX = window.getX();
            int maxX = minX + 15;
            int minY = window.getY();
            int maxY = minY + 15;
            if (Window.DEFAULT_X >= minX && Window.DEFAULT_X <= maxX
                    && Window.DEFAULT_Y >= minY && Window.DEFAULT_Y <= maxY) {
                return Vector2X.of(maxX, maxY);
            }
        }
        return Vector2X.of(Window.DEFAULT_X, Window.DEFAULT_Y);
    }

    /**
     * <p>
     *     Used when a new window is being created, to generate a random color for it.
     * </p>
     * <p>
     *     Each component of this color (red, green, blue) will be between 106 and 255, inclusive.
     *     The alpha is fixed at 111 (0x6F in hexadecimal), the standard transparency value for
     *     window backgrounds in Palladium Cheat Engine's click-GUI.
     * </p>
     *
     * @return a random color.
     */
    public int getNextColor() {
        int red = random.nextInt(150) + 106;
        int green = random.nextInt(150) + 106;
        int blue = random.nextInt(150) + 106;
        int alpha = 0x6F;
        return Colors.of(red, green, blue, alpha);
    }

    public @NotNull Vector2X<Integer> scaleMousePosition(double x, double y) {
        double width = MinecraftClient.getInstance().getWindow().getWidth();
        double height = MinecraftClient.getInstance().getWindow().getHeight();
        double scaledWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        double scaledHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
        int scaledX = (int) (x / (width / scaledWidth));
        int scaledY = (int) (y / (height / scaledHeight));
        return Vector2X.of(scaledX, scaledY);
    }
}
