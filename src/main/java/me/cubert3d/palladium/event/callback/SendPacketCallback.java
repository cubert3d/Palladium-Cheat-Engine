package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.Packet;

@ClassInfo(
        authors = "cubert3d",
        date = "7/25/2021",
        type = ClassType.CALLBACK
)

public interface SendPacketCallback {

    Event<SendPacketCallback> EVENT = EventFactory.createArrayBacked(SendPacketCallback.class,
            listeners -> (packet, isCancelled) -> {
                for (SendPacketCallback listener : listeners) {
                    listener.logPacket(packet, isCancelled);
                }
            });

    void logPacket(Packet<?> packet, boolean isCancelled);
}
