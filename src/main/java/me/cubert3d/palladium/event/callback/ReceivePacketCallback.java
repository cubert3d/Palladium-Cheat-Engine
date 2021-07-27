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

public interface ReceivePacketCallback {

    Event<ReceivePacketCallback> EVENT = EventFactory.createArrayBacked(ReceivePacketCallback.class,
            listeners -> packet -> {
                for (ReceivePacketCallback listener : listeners) {
                    listener.logPacket(packet);
                }
            });

    void logPacket(Packet<?> packet);
}
