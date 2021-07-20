package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.Packet;

@ClassInfo(
        authors = "cubert3d",
        date = "7/19/2021",
        type = ClassType.MIXIN
)


public interface SendPacketCallback {

    Event<SendPacketCallback> EVENT = EventFactory.createArrayBacked(SendPacketCallback.class,
            listeners -> packet -> {
                for (SendPacketCallback listener : listeners) {
                    boolean shouldCancel = listener.shouldCancel(packet);
                    if (shouldCancel) {
                        return true;
                    }
                }
                return false;
            });

    boolean shouldCancel(Packet<?> packet);
}
