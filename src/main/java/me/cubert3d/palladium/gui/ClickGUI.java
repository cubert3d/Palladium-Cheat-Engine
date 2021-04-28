package me.cubert3d.palladium.gui;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.gui.widget.*;
import me.cubert3d.palladium.input.Bindings;
import me.cubert3d.palladium.input.PlKeyBinding;
import me.cubert3d.palladium.module.ModuleGroup;
import me.cubert3d.palladium.module.modules.gui.EnabledModListModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.MinecraftClient;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/10/2021",
        status = "in-progress"
)

public final class ClickGUI {

    private static final PlKeyBinding binding = new PlKeyBinding("key.palladium.clickgui", 344, PlKeyBinding.Type.PRESS_ONCE) {
        @Override
        protected void onPressed() {
            toggleClickGUI();
        }
    };

    // Whether or not the ClickGUI is open.
    private static boolean open = false;

    /*
    Needed to determine whether the left mouse button is currently being pressed,
    because the method where the variable that holds this information is normally
    update, Mouse.onButtonPress(), is injected into for the ClickGUI, and that
    field mutation never happens if the ClickGUI is open. And so it needs to be
    tracked separately here.
     */
    private static boolean leftMouseButtonPressed = false;

    // The default windows that the ClickGUI begins with.
    private static final DisplayWindow enabledModulesWindow = new DisplayWindow("enabled_modules");
    private static final ModuleGroupWindow guiModulesWindow = new ModuleGroupWindow("gui_modules", ModuleGroup.MODULES_GUI);
    private static final ModuleGroupWindow renderModulesWindow = new ModuleGroupWindow("render_modules", ModuleGroup.MODULES_RENDER);
    private static final ModuleGroupWindow movementModulesWindow = new ModuleGroupWindow("movement_modules", ModuleGroup.MODULES_MOVEMENT);
    private static final ModuleGroupWindow playerModulesWindow = new ModuleGroupWindow("player_modules", ModuleGroup.MODULES_PLAYER);

    private ClickGUI() {}

    public static void loadBinding() {
        Bindings.addBinding(binding);
    }

    public static boolean isOpen() {
        return open;
    }

    public static void openClickGUI() {
        Common.getMC().mouse.unlockCursor();
        //KeyBinding.unpressAll();
        open = true;
    }

    public static void closeClickGUI() {
        Common.getMC().mouse.lockCursor();
        WidgetManager.resetClickedWidget();
        open = false;
    }

    public static void toggleClickGUI() {
        if (!open)
            openClickGUI();
        else
            closeClickGUI();
    }

    public static boolean shouldRender() {
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

    public static boolean isLeftMouseButtonPressed() {
        return leftMouseButtonPressed;
    }

    public static void toggleLeftMouseButtonPressed() {
        leftMouseButtonPressed = !leftMouseButtonPressed;
    }

    static {
        enabledModulesWindow.setX(25);
        enabledModulesWindow.setY(25);
        enabledModulesWindow.setWidth(150);
        enabledModulesWindow.setHeight(91);
        enabledModulesWindow.setColor(Colors.BACKGROUND_LAVENDER);
        enabledModulesWindow.setTextProvider(EnabledModListModule.modList);

        guiModulesWindow.setX(200);
        guiModulesWindow.setY(25);
        guiModulesWindow.setWidth(120);
        guiModulesWindow.setColor(Colors.BACKGROUND_YELLOW);

        renderModulesWindow.setX(25);
        renderModulesWindow.setY(150);
        renderModulesWindow.setWidth(120);
        renderModulesWindow.setColor(Colors.BACKGROUND_BLUE);

        movementModulesWindow.setX(160);
        movementModulesWindow.setY(150);
        movementModulesWindow.setWidth(120);
        movementModulesWindow.setColor(Colors.BACKGROUND_GREEN);

        playerModulesWindow.setX(300);
        playerModulesWindow.setY(150);
        playerModulesWindow.setWidth(120);
        playerModulesWindow.setColor(Colors.BACKGROUND_RED);
    }
}
