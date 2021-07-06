package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.callback.InventoryUpdateCallback;
import me.cubert3d.palladium.module.modules.player.BlinkModule;
import me.cubert3d.palladium.module.modules.render.FreecamModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/10/2021"
)

@Mixin(ClientPlayNetworkHandler.class)
abstract class ClientPlayNetworkHandlerMixin implements ClientPlayPacketListener {

    @Inject(at = @At(value = "INVOKE"),
            method = "sendPacket(Lnet/minecraft/network/Packet;)V",
            cancellable = true)
    private void onSendPacket(Packet<?> packet, final CallbackInfo info) {

        if (Palladium.getInstance().getModuleManager().isModuleEnabled(BlinkModule.class)) {
            info.cancel();
        }

        if (Palladium.getInstance().getModuleManager().isModuleEnabled(FreecamModule.class) && packet instanceof PlayerMoveC2SPacket) {
            info.cancel();
        }
    }

    @Inject(at = @At(value = "TAIL"),
            method = "onHealthUpdate(Lnet/minecraft/network/packet/s2c/play/HealthUpdateS2CPacket;)V")
    private void onHealthUpdateInject(@NotNull HealthUpdateS2CPacket packet, final CallbackInfo info) {


    }

    @Inject(at = @At(value = "TAIL"),
            method = "onInventory(Lnet/minecraft/network/packet/s2c/play/InventoryS2CPacket;)V")
    private void onInventoryInject(InventoryS2CPacket packet, final CallbackInfo info) {

        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        ActionResult result = InventoryUpdateCallback.EVENT.invoker().interact(player, packet);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
