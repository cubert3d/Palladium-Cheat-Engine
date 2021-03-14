package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.module.ModuleList;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/10/2021",
        status = "complete"
)

@Mixin(ClientPlayNetworkHandler.class)
public final class ClientPlayNetworkHandlerMixin {
    @Inject(at = @At(value = "INVOKE"),
            method = "sendPacket(Lnet/minecraft/network/Packet;)V",
            cancellable = true)
    private void onSendPacket(Packet<?> packet, final CallbackInfo info) {
        if (ModuleList.getModule("Blink").get().isEnabled()) {
            info.cancel();
        }
    }
}
