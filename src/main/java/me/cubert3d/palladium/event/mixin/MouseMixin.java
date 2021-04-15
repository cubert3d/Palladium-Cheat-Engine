package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.event.callback.MouseCallback;
import me.cubert3d.palladium.gui.ClickGUI;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/11/2021",
        status = "in-progress"
)

@Mixin(Mouse.class)
public final class MouseMixin {
        @Inject(at = @At(value = "TAIL"),
                method = "onMouseButton(JIII)V")
        private void onHungerUpdate(long window, int button, int action, int mods,
                                    CallbackInfo info) {

                MouseCallback.EVENT.invoker().interact();
        }
}