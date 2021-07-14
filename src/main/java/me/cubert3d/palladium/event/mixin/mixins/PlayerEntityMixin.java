package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.event.callback.FreecamCallback;
import me.cubert3d.palladium.event.mixin.MixinCaster;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/3/2021"
)

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin extends LivingEntity implements MixinCaster<PlayerEntity> {

    private PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isSpectator()Z"))
    private boolean isSpectatorRedirect(PlayerEntity player) {
        ActionResult result = FreecamCallback.EVENT.invoker().interact();
        if (result.equals(ActionResult.FAIL)) {
            return true;
        }
        else return player.isSpectator();
    }
}
