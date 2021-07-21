package me.cubert3d.palladium.event.mixin.mixins;

import com.mojang.authlib.GameProfile;
import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.callback.ClickTPRaycastCallback;
import me.cubert3d.palladium.event.callback.CommandCallback;
import me.cubert3d.palladium.event.callback.OverlayCallback;
import me.cubert3d.palladium.event.mixin.MixinCaster;
import me.cubert3d.palladium.module.modules.movement.SneakModule;
import me.cubert3d.palladium.module.modules.render.AntiOverlayModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClassInfo(
        authors = "cubert3d",
        date = "3/10/2021",
        type = ClassType.MIXIN
)

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity implements MixinCaster<ClientPlayerEntity> {

    private ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "sendChatMessage(Ljava/lang/String;)V",
            at = @At(value = "HEAD"),
            cancellable = true)
    private void sendChatMessageInject(String message, final CallbackInfo info) {
        ClientPlayerEntity player = self();
        boolean cancel = CommandCallback.EVENT.invoker().shouldCancel(player, message);
        if (cancel) {
            info.cancel();
        }
    }

    @Inject(method = "swingHand(Lnet/minecraft/util/Hand;)V",
            at = @At(value = "TAIL"),
            cancellable = true)
    private void swingHandInject(Hand hand, final CallbackInfo info) {
        ClickTPRaycastCallback.EVENT.invoker().interact(hand);
    }

    @Inject(method = "isSneaking()Z",
            at = @At(value = "HEAD"),
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
        boolean hideOverlay = OverlayCallback.EVENT.invoker().shouldHideOverlay(AntiOverlayModule.Overlay.NAUSEA);

        if (hideOverlay) {
            player.nextNauseaStrength = 0.0F;
            player.lastNauseaStrength = 0.0F;
            info.cancel();
        }
    }
}
