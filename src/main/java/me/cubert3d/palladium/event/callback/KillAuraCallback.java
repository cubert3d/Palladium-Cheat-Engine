package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@ClassInfo(
        authors = "cubert3d",
        date = "7/18/2021",
        type = ClassType.CALLBACK
)


public interface KillAuraCallback {

    Event<KillAuraCallback> EVENT = EventFactory.createArrayBacked(KillAuraCallback.class,
            listeners -> () -> {
                for (KillAuraCallback listener : listeners) {
                    listener.interact();
                }
            });

    void interact();
}
