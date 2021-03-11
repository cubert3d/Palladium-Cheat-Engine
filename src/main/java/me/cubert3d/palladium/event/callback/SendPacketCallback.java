package me.cubert3d.palladium.event.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.Packet;
import net.minecraft.util.ActionResult;

public interface SendPacketCallback {
    Event<SendPacketCallback> EVENT = EventFactory.createArrayBacked(SendPacketCallback.class,
            (listeners) -> (packet) -> {
                for (SendPacketCallback listener : listeners) {
                    ActionResult result = listener.interact(packet);

                    if (result != ActionResult.PASS)
                        return result;
                }
                return ActionResult.PASS;
            });

    ActionResult interact(Packet<?> packet);
}
