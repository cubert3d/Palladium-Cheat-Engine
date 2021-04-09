package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.event.callback.OverlayCallback;
import me.cubert3d.palladium.event.callback.PlayerChatCallback;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.modules.movement.ClickTPModule;
import me.cubert3d.palladium.module.modules.movement.SneakModule;
import me.cubert3d.palladium.module.modules.render.AntiOverlayModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.stat.StatHandler;
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
        date = "3/10/2021",
        status = "in-progress"
)

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {

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

    @Inject(at = @At(value = "TAIL"),
            method = "swingHand(Lnet/minecraft/util/Hand;)V",
            cancellable = true)
    private void onSwingHand(Hand hand, final CallbackInfo info) {

        ClickTPModule module = (ClickTPModule) ModuleManager.getModule("ClickTP").get();

        if (module.isEnabled() && hand.equals(ClickTPModule.HAND))
            module.teleport();
    }

    @Inject(at = @At(value = "HEAD"),
            method = "isSneaking()Z",
            cancellable = true)
    private void onIsSneaking(final CallbackInfoReturnable<Boolean> info) {
        if (ModuleManager.isModuleEnabled(SneakModule.class))
            info.setReturnValue(true);
    }

    @Inject(method = "updateNausea()V",
            at = @At("INVOKE"),
            cancellable = true)
    private void onUpdateNausea(CallbackInfo info) {
        @SuppressWarnings("ConstantConditions")
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        ActionResult result = OverlayCallback.EVENT.invoker().interact(AntiOverlayModule.Overlay.NAUSEA);

        if (result.equals(ActionResult.FAIL)) {
            player.nextNauseaStrength = 0.0F;
            player.lastNauseaStrength = 0.0F;
            info.cancel();
        }
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
