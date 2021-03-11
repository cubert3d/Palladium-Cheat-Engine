package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.event.callback.PlayerChatCallback;
import me.cubert3d.palladium.module.ModuleList;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public final class ClientPlayerEntityMixin {

    @Inject(at = @At(value = "HEAD"),
            method = "sendChatMessage(Ljava/lang/String;)V",
            cancellable = true)
    private void onSendChatMessage(String message, final CallbackInfo info) {

        @SuppressWarnings("ConstantConditions")
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

        ActionResult result = PlayerChatCallback.EVENT.invoker().interact(player, message);

        if (result.equals(ActionResult.FAIL))
            info.cancel();
    }

    @Inject(at = @At(value = "HEAD"),
            method = "isSneaking()Z",
            cancellable = true)
    private void onIsSneaking(final CallbackInfoReturnable<Boolean> info) {
        if (ModuleList.getModule("Sneak").isEnabled())
            info.setReturnValue(true);
    }

    /*
    @Redirect(method = "sendMovementPackets",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/entity/Entity;isSprinting()Z"))
    private boolean isSprintingRedirect(Entity entity) {
        if (ModuleList.getModule("Sprint").isEnabled())
            return true;
        else
            return entity.isSprinting();
    }
     */
}