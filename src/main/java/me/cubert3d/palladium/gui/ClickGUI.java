package me.cubert3d.palladium.gui;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/10/2021",
        status = "in-progress"
)

public final class ClickGUI {

    private static boolean open = false;

    private ClickGUI() {}

    public static boolean isOpen() {
        /*
        !open && !paused -> false
        open && !paused  -> true
        !open && paused  -> false
        open && paused   -> false
         */
        if (open)
            return !MinecraftClient.getInstance().isPaused();
        else
            return false;
    }

    public static void openClickGUI() {
        Common.getMC().mouse.unlockCursor();
        KeyBinding.unpressAll();
        open = true;
    }

    public static void closeClickGUI() {
        Common.getMC().mouse.lockCursor();
        open = false;
    }
}
