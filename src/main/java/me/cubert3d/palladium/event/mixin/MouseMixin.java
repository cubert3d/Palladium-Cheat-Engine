package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.util.Common;
import me.cubert3d.palladium.gui.ClickGUI;
import me.cubert3d.palladium.gui.widget.WidgetManager;
import me.cubert3d.palladium.input.Keys;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/11/2021",
        status = "in-progress"
)

@Mixin(Mouse.class)
abstract class MouseMixin {

    @Inject(at = @At(value = "HEAD"),
            method = "onMouseButton(JIII)V",
            cancellable = true)
    private void onMouseButtonInject(long window, int button, int action, int mods, CallbackInfo info) {
        /*
        Detects when the mouse is clicked, scales down the coordinates
        of the mouse to the current GUI scale, and sends them to the
        Widget Manager to determine if a widget was clicked.
         */

        // Only do anything if the mouse button is the left one.
        if (button != Keys.LEFT_MOUSE_BUTTON) {
            return;
        }

        Mouse mouse = (Mouse) (Object) this;

        // The absolute dimensions of the window.
        double width = Common.getMC().getWindow().getWidth();
        double height = Common.getMC().getWindow().getHeight();

        // The scaled-down dimensions of the window, as per the current GUI scale.
        double scaledWidth = Common.getMC().getWindow().getScaledWidth();
        double scaledHeight = Common.getMC().getWindow().getScaledHeight();

        // Position of the mouse; it needs to be scaled down.
        double x = mouse.getX();
        double y = mouse.getY();

        // Scale down the mouse coordinates.
        int scaledX = (int) (x / (width / scaledWidth));
        int scaledY = (int) (y / (height / scaledHeight));

        boolean isRelease = ClickGUI.isLeftMouseButtonPressed();
        ClickGUI.toggleLeftMouseButtonPressed();

        WidgetManager.onClick(scaledX, scaledY, isRelease);

        if (ClickGUI.shouldRender()) {
            info.cancel();
        }
    }

    @Inject(method = "onCursorPos(JDD)V", at = @At("TAIL"))
    private void onCursorPosInject(long window, double x, double y, CallbackInfo info) {
        if (ClickGUI.shouldRender()) {
            double width = Common.getMC().getWindow().getWidth();
            double height = Common.getMC().getWindow().getHeight();
            double scaledWidth = Common.getMC().getWindow().getScaledWidth();
            double scaledHeight = Common.getMC().getWindow().getScaledHeight();
            int scaledX = (int) (x / (width / scaledWidth));
            int scaledY = (int) (y / (height / scaledHeight));
            WidgetManager.onMouseMove(scaledX, scaledY);
        }
    }
}
