package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.event.callback.PlayerJumpCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public final class PlayerEntityMixin {
    @Inject(at = @At(value = "TAIL"),
            method = "jump()V",
            cancellable = true)
    private void onJump(final CallbackInfo info) {
        ActionResult result = PlayerJumpCallback.EVENT.invoker().interact((PlayerEntity) (Object) this);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
