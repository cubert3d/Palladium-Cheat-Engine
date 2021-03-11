package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import me.cubert3d.palladium.util.annotation.DebugOnly;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.util.ActionResult;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/8/2021",
        status = "complete"
)

@DebugOnly
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
