package me.cubert3d.palladium.event.listener;

import me.cubert3d.palladium.event.callback.SendPacketCallback;
import me.cubert3d.palladium.util.annotation.DebugOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.util.ActionResult;

@DebugOnly
public final class PacketListener {

    public static void registerListener() {
        SendPacketCallback.EVENT.register(packet -> {

            /*
            if (packet instanceof ClickSlotC2SPacket) {
                ClickSlotC2SPacket clickSlotPacket = (ClickSlotC2SPacket) packet;
                ItemStack stack = clickSlotPacket.getStack();

                System.out.println("_");
                System.out.println("BEGIN CLICKSLOT PACKET");

                System.out.println("PACKET SYNC ID: " + clickSlotPacket.getSyncId());
                System.out.println("PACKET CLICK DATA: " + clickSlotPacket.getClickData());
                System.out.println("PACKET ACTION TYPE: " + clickSlotPacket.getActionType());
                System.out.println("PACKET ACTION ID: " + clickSlotPacket.getActionId());

                System.out.println("PACKET SLOT: " + clickSlotPacket.getSlot());
                System.out.println("PACKET STACK NAME: " + stack.getName().getString());
            }
             */

            return ActionResult.PASS;
        });
    }
}
