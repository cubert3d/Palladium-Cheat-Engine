package me.cubert3d.palladium.gui;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.gui.window.ModuleGroupWindow;
import me.cubert3d.palladium.gui.window.WindowManager;
import me.cubert3d.palladium.module.ModuleGroupManager;
import me.cubert3d.palladium.util.Vector2X;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

@ClassInfo(
        description = "Manages and renders a GUI that allows control over modules.",
        authors = "cubert3d",
        date = "4/10/2021",
        type = ClassType.RENDERER
)

public final class ClickGUI {

    // Whether or not the ClickGUI is open.
    private boolean open = false;
    /*
    Needed to determine whether the left mouse button is currently being pressed,
    because the method where the variable that holds this information is normally
    update, Mouse.onButtonPress(), is injected into for the ClickGUI, and that
    field mutation never happens if the ClickGUI is open. And so it needs to be
    tracked separately here.
     */
    private boolean leftMouseButtonPressed = false;
    private final WindowManager windowManager;

    ClickGUI() {
        this.windowManager = new WindowManager(this);
    }

    public void initialize() {

        ModuleGroupManager moduleGroupManager = Palladium.getInstance().getModuleGroupManager();

        Palladium.getLogger().info("Initializing ClickGUI...");

        // The default windows that the ClickGUI begins with.
        ModuleGroupWindow guiModulesWindow = ModuleGroupWindow.newModuleGroupWindow(moduleGroupManager.getGroup("gui"));
        guiModulesWindow.setX(200);
        guiModulesWindow.setY(25);
        guiModulesWindow.setWidth(120);
        guiModulesWindow.setColor(Colors.BACKGROUND_YELLOW);
        guiModulesWindow.open();

        ModuleGroupWindow renderModulesWindow = ModuleGroupWindow.newModuleGroupWindow(moduleGroupManager.getGroup("render"));
        renderModulesWindow.setX(25);
        renderModulesWindow.setY(150);
        renderModulesWindow.setWidth(120);
        renderModulesWindow.setColor(Colors.BACKGROUND_BLUE);
        renderModulesWindow.open();

        ModuleGroupWindow movementModulesWindow = ModuleGroupWindow.newModuleGroupWindow(moduleGroupManager.getGroup("movement"));
        movementModulesWindow.setX(160);
        movementModulesWindow.setY(150);
        movementModulesWindow.setWidth(120);
        movementModulesWindow.setColor(Colors.BACKGROUND_GREEN);
        movementModulesWindow.open();

        ModuleGroupWindow playerModulesWindow = ModuleGroupWindow.newModuleGroupWindow(moduleGroupManager.getGroup("player"));
        playerModulesWindow.setX(300);
        playerModulesWindow.setY(150);
        playerModulesWindow.setWidth(120);
        playerModulesWindow.setColor(Colors.BACKGROUND_RED);
        playerModulesWindow.open();

        ModuleGroupWindow combatModulesWindow = ModuleGroupWindow.newModuleGroupWindow(moduleGroupManager.getGroup("combat"));
        combatModulesWindow.setX(400);
        combatModulesWindow.setY(150);
        combatModulesWindow.setWidth(120);
        combatModulesWindow.setColor(Colors.BACKGROUND_RED);
        combatModulesWindow.open();

        Palladium.getLogger().info("Done initializing ClickGUI!");
    }

    public final WindowManager getWindowManager() {
        return windowManager;
    }

    public final boolean isOpen() {
        return open;
    }

    public final void open() {
        MinecraftClient.getInstance().mouse.unlockCursor();
        //KeyBinding.unpressAll();
        open = true;
    }

    public final void close() {
        MinecraftClient.getInstance().mouse.lockCursor();
        windowManager.resetDraggedWindow();
        open = false;
    }

    public final boolean shouldRender() {
        /*
        !open && !paused -> false
        open && !paused  -> true
        !open && paused  -> false
        open && paused   -> false
         */
        if (!MinecraftClient.getInstance().isPaused() && MinecraftClient.getInstance().currentScreen == null) {
            return isOpen();
        }
        else {
            return false;
        }
    }

    final void render(MatrixStack matrices) {
        windowManager.renderWindows(matrices);
    }

    public final void onMouseButton(@NotNull Mouse mouse) {

        // The absolute dimensions of the window.
        double width = MinecraftClient.getInstance().getWindow().getWidth();
        double height = MinecraftClient.getInstance().getWindow().getHeight();

        // The scaled-down dimensions of the window, as per the current GUI scale.
        double scaledWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        double scaledHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

        // Position of the mouse; it needs to be scaled down.
        double x = mouse.getX();
        double y = mouse.getY();

        // Scale down the mouse coordinates.
        int scaledX = (int) (x / (width / scaledWidth));
        int scaledY = (int) (y / (height / scaledHeight));

        boolean isRelease = isLeftMouseButtonPressed();
        toggleLeftMouseButtonPressed();

        windowManager.onClick(scaledX, scaledY, isRelease);
    }

    public final void onMouseCursorMove(double x, double y) {
        if (shouldRender()) {
            Vector2X<Integer> scaledMousePosition = windowManager.scaleMousePosition(x, y);
            windowManager.onMouseMove(scaledMousePosition.getX(), scaledMousePosition.getY());
        }
    }

    public final boolean isLeftMouseButtonPressed() {
        return leftMouseButtonPressed;
    }

    public final void toggleLeftMouseButtonPressed() {
        this.leftMouseButtonPressed = !leftMouseButtonPressed;
    }
}
