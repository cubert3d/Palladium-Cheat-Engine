package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.util.Common;
import me.cubert3d.palladium.event.callback.InventoryUpdateCallback;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/8/2021",
        status = "benched"
)

@Mixin(ClientPlayNetworkHandler.class)
abstract class InventoryUpdateMixin {
    @Inject(at = @At(value = "TAIL"),
            method = "onInventory(Lnet/minecraft/network/packet/s2c/play/InventoryS2CPacket;)V")
    private void onInventoryUpdate(InventoryS2CPacket packet, final CallbackInfo info) {

        PlayerEntity player = Common.getPlayer();

        ActionResult result = InventoryUpdateCallback.EVENT.invoker().interact(player, packet);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
