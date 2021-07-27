package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.event.callback.ReceivePacketCallback;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.util.thread.ThreadExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassInfo(
        authors = "cubert3d",
        date = "7/26/2021",
        type = ClassType.MIXIN
)

@Mixin(NetworkThreadUtils.class)
public class NetworkThreadUtilsMixin {

    @Inject(method = "forceMainThread(" +
            "Lnet/minecraft/network/Packet;" +
            "Lnet/minecraft/network/listener/PacketListener;" +
            "Lnet/minecraft/util/thread/ThreadExecutor;" +
            ")V", at = @At("TAIL"))
    private static <T extends PacketListener> void forceMainThread(Packet<T> packet, T listener, ThreadExecutor<?> engine, final CallbackInfo info) {
        ReceivePacketCallback.EVENT.invoker().logPacket(packet);
    }
}
