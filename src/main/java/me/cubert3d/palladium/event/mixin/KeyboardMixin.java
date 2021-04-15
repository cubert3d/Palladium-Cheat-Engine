package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.input.Bindings;
import me.cubert3d.palladium.input.PalladiumKeyBinding;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public final class KeyboardMixin {
    @Inject(method = "onKey(JIIII)V", at = @At("TAIL"))
    private void onOnKey(long window, int key, int scancode, int i, int j, CallbackInfo info) {
        Bindings.getBindingFromCode(key).ifPresent(PalladiumKeyBinding::trigger);
    }
}
