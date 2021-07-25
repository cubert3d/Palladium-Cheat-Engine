package me.cubert3d.palladium.gui.window;

import me.cubert3d.palladium.gui.ClickGUI;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.util.Vector2X;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

@ClassInfo(
        authors = "cubert3d",
        date = "7/24/2021",
        type = ClassType.MANAGER
)

public final class WindowManager {

    private final Set<Window> windows;
    private final Random random;
    private Window draggedWindow;
    private final ClickGUI clickGUI;

    public WindowManager(ClickGUI clickGUI) {
        this.windows = new HashSet<>();
        this.random = new Random();
        this.clickGUI = clickGUI;
    }

    public void openWindow(Window window) {
        this.windows.add(window);
    }

    public void closeWindow(Window window) {
        this.windows.remove(window);
    }

    public final void setDraggedWindow(Window draggedWindow) {
        this.draggedWindow = draggedWindow;
    }

    public final void resetDraggedWindow() {
        this.draggedWindow = null;
    }

    private boolean isClickable() {
        return clickGUI.shouldRender();
    }

    // Called when the screen is clicked; checks each of the windows to see if the mouse was on it.
    public final void onClick(int mouseX, int mouseY, boolean isRelease) {
        if (isClickable()) {
            // Iterator is used because the window might be closed (ie removed from the set) during the loop
            Window nextWindow;
            Iterator<Window> iterator = windows.iterator();
            while (iterator.hasNext()) {
                nextWindow = iterator.next();
                if (nextWindow.isClicked(mouseX, mouseY)) {
                    nextWindow.onClick(mouseX, mouseY, isRelease);
                    break;
                }
            }
            if (isRelease && draggedWindow != null) {
                resetDraggedWindow();
            }
        }
    }

    public final void onMouseMove(int mousePosX, int mousePosY) {
        if (draggedWindow != null) {
            draggedWindow.drag(mousePosX, mousePosY);
        }
    }

    public final void renderWindows(MatrixStack matrices) {
        for (Window window : windows) {
            if (window.shouldRender(clickGUI)) {
                window.render(matrices);
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
     * @return the position at which to open the next window
     */
    public @NotNull Vector2X<Integer> getNextWindowPosition() {
        for (Window window : windows) {
            int minX = window.getX();
            int maxX = minX + 5;
            int minY = window.getY();
            int maxY = minY + 5;
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
     * @return a random color.
     */
    public int getNextColor() {
        int red = random.nextInt(100) + 126;
        int green = random.nextInt(100) + 126;
        int blue = random.nextInt(100) + 126;
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
