package me.cubert3d.palladium.gui;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.gui.widget.DisplayWindow;
import me.cubert3d.palladium.gui.widget.ModuleGroupWindow;
import me.cubert3d.palladium.gui.widget.Widget;
import me.cubert3d.palladium.gui.widget.WidgetManager;
import me.cubert3d.palladium.module.ModuleGroupManager;
import me.cubert3d.palladium.module.modules.gui.EnabledModListModule;
import me.cubert3d.palladium.util.Common;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/10/2021",
        status = "in-progress"
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
    private final WidgetManager widgetManager;

    ClickGUI() {
        this.widgetManager = new WidgetManager(this);
    }

    public void initialize() {

        ModuleGroupManager moduleGroupManager = Palladium.getInstance().getModuleGroupManager();

        Palladium.getLogger().info("Initializing ClickGUI...");

        // The default windows that the ClickGUI begins with.
        DisplayWindow enabledModulesWindow = new DisplayWindow("enabled_modules", widgetManager);
        enabledModulesWindow.setX(25);
        enabledModulesWindow.setY(25);
        enabledModulesWindow.setWidth(150);
        enabledModulesWindow.setHeight(91);
        enabledModulesWindow.setColor(Colors.BACKGROUND_LAVENDER);
        enabledModulesWindow.setTextProvider(EnabledModListModule.modList);

        ModuleGroupWindow guiModulesWindow = new ModuleGroupWindow("gui_modules", Palladium.getInstance().getModuleGroupManager().getGroup("gui"), widgetManager);
        guiModulesWindow.setX(200);
        guiModulesWindow.setY(25);
        guiModulesWindow.setWidth(120);
        guiModulesWindow.setColor(Colors.BACKGROUND_YELLOW);

        ModuleGroupWindow renderModulesWindow = new ModuleGroupWindow("render_modules", moduleGroupManager.getGroup("render"), widgetManager);
        renderModulesWindow.setX(25);
        renderModulesWindow.setY(150);
        renderModulesWindow.setWidth(120);
        renderModulesWindow.setColor(Colors.BACKGROUND_BLUE);

        ModuleGroupWindow movementModulesWindow = new ModuleGroupWindow("movement_modules", moduleGroupManager.getGroup("movement"), widgetManager);
        movementModulesWindow.setX(160);
        movementModulesWindow.setY(150);
        movementModulesWindow.setWidth(120);
        movementModulesWindow.setColor(Colors.BACKGROUND_GREEN);

        ModuleGroupWindow playerModulesWindow = new ModuleGroupWindow("player_modules", moduleGroupManager.getGroup("player"), widgetManager);
        playerModulesWindow.setX(300);
        playerModulesWindow.setY(150);
        playerModulesWindow.setWidth(120);
        playerModulesWindow.setColor(Colors.BACKGROUND_RED);

        Palladium.getLogger().info("Done initializing ClickGUI!");
    }

    public final WidgetManager getWidgetManager() {
        return widgetManager;
    }

    public final boolean isOpen() {
        return open;
    }

    public final void open() {
        Common.getMC().mouse.unlockCursor();
        //KeyBinding.unpressAll();
        open = true;
    }

    public final void close() {
        MinecraftClient.getInstance().mouse.lockCursor();
        widgetManager.resetClickedWidget();
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
        for (Widget widget : widgetManager.getWidgets()) {
            if (widget.shouldRender()) {
                widget.render(matrices);
            }
        }
    }

    public final void onMouseButton(@NotNull Mouse mouse) {

        // The absolute dimensions of the window.
        double width = MinecraftClient.getInstance().getWindow().getWidth();
        double height = MinecraftClient.getInstance().getWindow().getHeight();

        // The scaled-down dimensions of the window, as per the current GUI scale.
        double scaledWidth = Common.getMC().getWindow().getScaledWidth();
        double scaledHeight = Common.getMC().getWindow().getScaledHeight();

        // Position of the mouse; it needs to be scaled down.
        double x = mouse.getX();
        double y = mouse.getY();

        // Scale down the mouse coordinates.
        int scaledX = (int) (x / (width / scaledWidth));
        int scaledY = (int) (y / (height / scaledHeight));

        boolean isRelease = isLeftMouseButtonPressed();
        toggleLeftMouseButtonPressed();

        widgetManager.onClick(scaledX, scaledY, isRelease);
    }

    public final void onMouseCursorMove(double x, double y) {
        if (shouldRender()) {
            double width = Common.getMC().getWindow().getWidth();
            double height = Common.getMC().getWindow().getHeight();
            double scaledWidth = Common.getMC().getWindow().getScaledWidth();
            double scaledHeight = Common.getMC().getWindow().getScaledHeight();
            int scaledX = (int) (x / (width / scaledWidth));
            int scaledY = (int) (y / (height / scaledHeight));
            widgetManager.onMouseMove(scaledX, scaledY);
        }
    }

    public final boolean isLeftMouseButtonPressed() {
        return leftMouseButtonPressed;
    }

    public final void toggleLeftMouseButtonPressed() {
        this.leftMouseButtonPressed = !leftMouseButtonPressed;
    }
}
