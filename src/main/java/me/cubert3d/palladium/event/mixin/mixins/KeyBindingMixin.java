package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.event.callback.KeyPressedCallback;
import me.cubert3d.palladium.event.mixin.MixinCaster;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClassInfo(
        authors = "cubert3d",
        date = "6/28/2021",
        type = ClassType.MIXIN
)

@Mixin(KeyBinding.class)
public abstract class KeyBindingMixin implements Comparable<KeyBinding>, MixinCaster<KeyBinding> {

    @Inject(method = "isPressed()Z", at = @At("HEAD"), cancellable = true)
    private void isPressedInject(final CallbackInfoReturnable<Boolean> info) {
        boolean forcePress = KeyPressedCallback.EVENT.invoker().shouldForcePress(self());
        if (forcePress) {
            info.setReturnValue(true);
        }
    }
}
