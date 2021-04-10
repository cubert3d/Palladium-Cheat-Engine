package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import me.cubert3d.palladium.util.annotation.DebugOnly;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.Packet;
import net.minecraft.util.ActionResult;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/7/2021"
)

@DebugOnly
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
