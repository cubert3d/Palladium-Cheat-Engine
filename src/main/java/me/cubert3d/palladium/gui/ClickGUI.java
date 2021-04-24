package me.cubert3d.palladium.gui;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.gui.widget.DisplayWindowWidget;
import me.cubert3d.palladium.input.Bindings;
import me.cubert3d.palladium.input.PlKeyBinding;
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

    private static boolean open = false;
    private static final PlKeyBinding binding = new PlKeyBinding("key.palladium.clickgui", 344, PlKeyBinding.Type.PRESS_ONCE) {
        @Override
        protected void onPressed() {
            toggleClickGUI();
        }
    };

    private static final DisplayWindowWidget testWidget = new DisplayWindowWidget("test_widget", "Enabled Modules");

    private ClickGUI() {}

    public static void loadBinding() {
        Bindings.addBinding(binding);
    }

    public static boolean isOpen() {
        return open;
    }

    public static boolean shouldRender() {
        /*
        !open && !paused -> false
        open && !paused  -> true
        !open && paused  -> false
        open && paused   -> false
         */
        if (isOpen())
            return !MinecraftClient.getInstance().isPaused();
        else
            return false;
    }

    public static void openClickGUI() {
        Common.getMC().mouse.unlockCursor();
        //KeyBinding.unpressAll();
        open = true;
    }

    public static void closeClickGUI() {
        Common.getMC().mouse.lockCursor();
        open = false;
    }

    public static void toggleClickGUI() {
        if (!open)
            openClickGUI();
        else
            closeClickGUI();
    }

    static {
        testWidget.setX(25);
        testWidget.setY(25);
        testWidget.setWidth(150);
        testWidget.setHeight(91);
        testWidget.setColor(Colors.BACKGROUND_LAVENDER);
        testWidget.setTextList(EnabledModListModule.modList);
    }
}
