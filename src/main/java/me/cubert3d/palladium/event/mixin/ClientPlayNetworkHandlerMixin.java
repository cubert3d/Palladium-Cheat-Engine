package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.event.callback.SendPacketCallback;
import me.cubert3d.palladium.module.ModuleList;
import me.cubert3d.palladium.util.annotation.DebugOnly;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.KeepAliveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@DebugOnly
@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(at = @At(value = "INVOKE"),
            method = "sendPacket(Lnet/minecraft/network/Packet;)V",
            cancellable = true)
    private void onSendPacket(Packet<?> packet, final CallbackInfo info) {
        if (ModuleList.getModule("Blink").isEnabled()) {
            info.cancel();
        }
    }
}
