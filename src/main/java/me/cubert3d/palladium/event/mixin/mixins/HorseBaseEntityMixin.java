package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.event.callback.EntityControlCallback;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HorseBaseEntity.class)
abstract class HorseBaseEntityMixin {

    @Inject(method = "isTame()Z", at = @At("HEAD"), cancellable = true)
    private void isTameInject(CallbackInfoReturnable<Boolean> info) {
        ActionResult result = EntityControlCallback.EVENT.invoker().interact();
        if (result.equals(ActionResult.SUCCESS)) {
            info.setReturnValue(true);
        }
    }

    @Inject(method = "isSaddled()Z", at = @At("HEAD"), cancellable = true)
    private void isSaddledInject(CallbackInfoReturnable<Boolean> info) {
        ActionResult result = EntityControlCallback.EVENT.invoker().interact();
        if (result.equals(ActionResult.SUCCESS)) {
            info.setReturnValue(true);
        }
    }
}
