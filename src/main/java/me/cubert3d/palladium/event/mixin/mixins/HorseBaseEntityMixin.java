package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.event.callback.EntityControlCallback;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClassInfo(
        authors = "cubert3d",
        date = "7/9/2021",
        type = ClassType.MIXIN
)

@Mixin(HorseBaseEntity.class)
abstract class HorseBaseEntityMixin {

    /*
    The method "isTame" must be injected into so that the horse does not buck the player.
     */
    @Inject(method = "isTame()Z", at = @At("HEAD"), cancellable = true)
    private void isTameInject(CallbackInfoReturnable<Boolean> info) {
        ActionResult result = EntityControlCallback.EVENT.invoker().interact();
        if (result.equals(ActionResult.SUCCESS)) {
            info.setReturnValue(true);
        }
    }

    /*
    Only target the "isSaddled" call in "travel" so that the entity-control module does
    not render saddles on all horses, regardless of whether they actually have saddles.
     */
    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/HorseBaseEntity;isSaddled()Z"))
    private boolean isSaddledRedirect(HorseBaseEntity horse) {
        ActionResult result = EntityControlCallback.EVENT.invoker().interact();
        if (result.equals(ActionResult.SUCCESS)) {
            return true;
        }
        else {
            return horse.isSaddled();
        }
    }

    /*
    The method "canJump" must be injected into because it relies on the "isSaddled" method,
    which is only being redirected inside the "travel" method.
     */
    @Inject(method = "canJump()Z", at = @At("HEAD"), cancellable = true)
    private void canJumpInject(CallbackInfoReturnable<Boolean> info) {
        HorseBaseEntity horse = (HorseBaseEntity) (Object) this;
        ActionResult result = EntityControlCallback.EVENT.invoker().interact();
        if (result.equals(ActionResult.SUCCESS)) {
            info.setReturnValue(true);
        }
    }
}
