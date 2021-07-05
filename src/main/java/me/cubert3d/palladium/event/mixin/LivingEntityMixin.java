package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.event.callback.OverlayCallback;
import me.cubert3d.palladium.module.modules.render.AntiOverlayModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/8/2021"
)

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin {
    @Inject(method = "hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z",
            at = @At("HEAD"), cancellable = true)
    private void onHasStatusEffect(StatusEffect effect, CallbackInfoReturnable<Boolean> info) {

        ActionResult resultNausea = OverlayCallback.EVENT.invoker().interact(AntiOverlayModule.Overlay.NAUSEA);
        ActionResult resultBlindness = OverlayCallback.EVENT.invoker().interact(AntiOverlayModule.Overlay.BLINDNESS);

        if ((resultNausea.equals(ActionResult.FAIL) && effect.equals(StatusEffects.NAUSEA))
                || (resultBlindness.equals(ActionResult.FAIL) && effect.equals(StatusEffects.BLINDNESS)))
            info.setReturnValue(false);
    }
}
