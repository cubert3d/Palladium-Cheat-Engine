package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.event.callback.OverlayCallback;
import me.cubert3d.palladium.event.callback.ToolSaverCallback;
import me.cubert3d.palladium.event.mixin.MixinCaster;
import me.cubert3d.palladium.module.modules.render.AntiOverlayModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClassInfo(
        authors = "cubert3d",
        date = "4/8/2021",
        type = ClassType.MIXIN
)

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements MixinCaster<LivingEntity> {

    private LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At("HEAD"))
    private void damageInject(@NotNull DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        if (self().equals(MinecraftClient.getInstance().player)) {
            ToolSaverCallback.EVENT.invoker().interact(true);
        }
    }

    @Inject(method = "hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z",
            at = @At("HEAD"), cancellable = true)
    private void hasStatusEffectInject(StatusEffect effect, final CallbackInfoReturnable<Boolean> info) {

        boolean resultNausea = OverlayCallback.EVENT.invoker().shouldHideOverlay(AntiOverlayModule.Overlay.NAUSEA)
                && effect.equals(StatusEffects.NAUSEA);
        boolean resultBlindness = OverlayCallback.EVENT.invoker().shouldHideOverlay(AntiOverlayModule.Overlay.BLINDNESS)
                && effect.equals(StatusEffects.BLINDNESS);

        if (self().equals(MinecraftClient.getInstance().player)) {
            if (resultNausea || resultBlindness) {
                info.setReturnValue(false);
            }
        }
    }
}
