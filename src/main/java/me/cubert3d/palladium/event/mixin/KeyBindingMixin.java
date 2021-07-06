package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.module.modules.movement.SprintModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBinding.class)
abstract class KeyBindingMixin implements Comparable<KeyBinding> {

    @Inject(method = "isPressed()Z", at = @At("HEAD"), cancellable = true)
    private void isPressedInject(CallbackInfoReturnable<Boolean> info) {
        KeyBinding binding = (KeyBinding) (Object) this;
        if (binding.equals(MinecraftClient.getInstance().options.keySprint)
                && Palladium.getInstance().getModuleManager().isModuleEnabled(SprintModule.class)) {
            info.setReturnValue(true);
        }
    }
}
