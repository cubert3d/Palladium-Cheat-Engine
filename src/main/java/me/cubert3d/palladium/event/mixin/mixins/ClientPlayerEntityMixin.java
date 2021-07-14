package me.cubert3d.palladium.event.mixin.mixins;

import com.mojang.authlib.GameProfile;
import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.callback.ClickTPRaycastCallback;
import me.cubert3d.palladium.event.callback.CommandCallback;
import me.cubert3d.palladium.event.callback.OverlayCallback;
import me.cubert3d.palladium.event.mixin.MixinCaster;
import me.cubert3d.palladium.module.modules.movement.SneakModule;
import me.cubert3d.palladium.module.modules.render.AntiOverlayModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/10/2021"
)

@Mixin(ClientPlayerEntity.class)
abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity implements MixinCaster<ClientPlayerEntity> {

    private ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(at = @At(value = "HEAD"),
            method = "sendChatMessage(Ljava/lang/String;)V",
            cancellable = true)
    private void sendChatMessageInject(String message, final CallbackInfo info) {

        ClientPlayerEntity player = self();

        ActionResult result = CommandCallback.EVENT.invoker().interact(player, message);

        if (result.equals(ActionResult.FAIL))
            info.cancel();
    }

    @Inject(at = @At(value = "TAIL"),
            method = "swingHand(Lnet/minecraft/util/Hand;)V",
            cancellable = true)
    private void swingHandInject(Hand hand, final CallbackInfo info) {
        ClickTPRaycastCallback.EVENT.invoker().interact(hand);
    }

    @Inject(at = @At(value = "HEAD"),
            method = "isSneaking()Z",
            cancellable = true)
    private void isSneakingInject(final CallbackInfoReturnable<Boolean> info) {
        if (Palladium.getInstance().getModuleManager().isModuleEnabled(SneakModule.class)) {
            info.setReturnValue(true);
        }
    }

    @Inject(method = "updateNausea()V",
            at = @At("INVOKE"),
            cancellable = true)
    private void updateNauseaInject(final CallbackInfo info) {
        ClientPlayerEntity player = self();
        ActionResult result = OverlayCallback.EVENT.invoker().interact(AntiOverlayModule.Overlay.NAUSEA);

        if (result.equals(ActionResult.FAIL)) {
            player.nextNauseaStrength = 0.0F;
            player.lastNauseaStrength = 0.0F;
            info.cancel();
        }
    }
}
