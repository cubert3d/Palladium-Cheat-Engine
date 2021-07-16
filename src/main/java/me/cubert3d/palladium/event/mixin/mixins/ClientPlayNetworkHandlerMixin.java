package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.callback.FreecamCallback;
import me.cubert3d.palladium.event.callback.HealthUpdateCallback;
import me.cubert3d.palladium.module.modules.player.BlinkModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassInfo(
        authors = "cubert3d",
        date = "3/10/2021",
        type = ClassType.MIXIN
)

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin implements ClientPlayPacketListener {

    @Inject(at = @At(value = "HEAD"),
            method = "sendPacket(Lnet/minecraft/network/Packet;)V",
            cancellable = true)
    private void onSendPacket(Packet<?> packet, final CallbackInfo info) {

        ActionResult freecamResult = FreecamCallback.EVENT.invoker().interact();

        if (Palladium.getInstance().getModuleManager().isModuleEnabled(BlinkModule.class)) {
            info.cancel();
        }

        if (freecamResult.equals(ActionResult.FAIL) && packet instanceof PlayerMoveC2SPacket) {
            info.cancel();
        }

        if (packet instanceof ClickSlotC2SPacket) {
            printClickPacket((ClickSlotC2SPacket) packet);
        }
    }

    @Inject(at = @At(value = "TAIL"),
            method = "onHealthUpdate(Lnet/minecraft/network/packet/s2c/play/HealthUpdateS2CPacket;)V")
    private void onHealthUpdateInject(@NotNull HealthUpdateS2CPacket packet, final CallbackInfo info) {
        HealthUpdateCallback.EVENT.invoker().interact(packet.getHealth());
    }

    private static void printClickPacket(ClickSlotC2SPacket packet) {
        System.out.println();
        System.out.println("ClickSlotPacket");
        System.out.println("slot: " + packet.getSlot());
        System.out.println("clickData: " + packet.getClickData());
        System.out.println("stack: " + packet.getStack().getName().getString());
        System.out.println("actionType: " + packet.getActionType().toString());
        System.out.println();
    }

    /*
    [19:02:01] [main/INFO] (Minecraft) [STDOUT]: ClickSlotPacket
    [19:02:01] [main/INFO] (Minecraft) [STDOUT]: slot: 6
    [19:02:01] [main/INFO] (Minecraft) [STDOUT]: clickData: 0
    [19:02:01] [main/INFO] (Minecraft) [STDOUT]: stack: Air
    [19:02:01] [main/INFO] (Minecraft) [STDOUT]: actionType: QUICK_MOVE



    [19:02:06] [main/INFO] (Minecraft) [STDOUT]: ClickSlotPacket
    [19:02:06] [main/INFO] (Minecraft) [STDOUT]: slot: 24
    [19:02:06] [main/INFO] (Minecraft) [STDOUT]: clickData: 0
    [19:02:06] [main/INFO] (Minecraft) [STDOUT]: stack: Air
    [19:02:06] [main/INFO] (Minecraft) [STDOUT]: actionType: QUICK_MOVE
     */
}
