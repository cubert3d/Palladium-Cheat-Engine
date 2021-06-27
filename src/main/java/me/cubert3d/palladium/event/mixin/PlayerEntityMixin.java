package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.event.callback.PlayerJumpCallback;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.modules.render.FreecamModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/3/2021"
)

@Mixin(PlayerEntity.class)
public final class PlayerEntityMixin {

    @Inject(at = @At(value = "TAIL"),
            method = "jump()V",
            cancellable = true)
    private void jumpInject(final CallbackInfo info) {
        ActionResult result = PlayerJumpCallback.EVENT.invoker().interact((PlayerEntity) (Object) this);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isSpectator()Z"))
    private boolean isSpectatorRedirect(PlayerEntity player) {
        if (ModuleManager.isModuleEnabled(FreecamModule.class)) {
            return true;
        }
        else return player.isSpectator();
    }
}
