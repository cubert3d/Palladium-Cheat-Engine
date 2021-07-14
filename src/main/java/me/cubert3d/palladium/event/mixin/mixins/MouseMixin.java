package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.mixin.MixinCaster;
import me.cubert3d.palladium.gui.ClickGUI;
import me.cubert3d.palladium.input.Keys;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassInfo(
        authors = "cubert3d",
        date = "3/11/2021",
        type = ClassType.MIXIN
)

@Mixin(Mouse.class)
abstract class MouseMixin implements MixinCaster<Mouse> {

    @Inject(at = @At(value = "HEAD"),
            method = "onMouseButton(JIII)V",
            cancellable = true)
    private void onMouseButtonInject(long window, int button, int action, int mods, final CallbackInfo info) {
        /*
        Detects when the mouse is clicked, scales down the coordinates
        of the mouse to the current GUI scale, and sends them to the
        Widget Manager to determine if a widget was clicked.
         */
        ClickGUI clickGUI = Palladium.getInstance().getGuiRenderer().getClickGUI();

        // Only do anything if the mouse button is the left one.
        if (button == Keys.LEFT_MOUSE_BUTTON && clickGUI.shouldRender()) {
            clickGUI.onMouseButton(self());
            info.cancel();
        }
    }

    @Inject(method = "onCursorPos(JDD)V", at = @At("TAIL"))
    private void onCursorPosInject(long window, double x, double y, CallbackInfo info) {
        Palladium.getInstance().getGuiRenderer().getClickGUI().onMouseCursorMove(x, y);
    }
}
