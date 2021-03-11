package me.cubert3d.palladium.event.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.util.ActionResult;

public interface InventoryUpdateCallback {
    Event<InventoryUpdateCallback> EVENT = EventFactory.createArrayBacked(InventoryUpdateCallback.class,
            (listeners) -> (player, packet) -> {
                for (InventoryUpdateCallback listener : listeners) {
                    ActionResult result = listener.interact(player, packet);

                    if (result != ActionResult.PASS)
                        return result;
                }
                return ActionResult.PASS;
            });

    ActionResult interact(PlayerEntity player, InventoryS2CPacket packet);
}
