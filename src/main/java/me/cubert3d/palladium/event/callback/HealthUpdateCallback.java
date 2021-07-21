package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.event.mixin.mixins.ClientPlayNetworkHandlerMixin;
import me.cubert3d.palladium.module.modules.player.AutoDisconnectModule;
import me.cubert3d.palladium.util.annotation.CallbackInfo;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.annotation.Interaction;
import me.cubert3d.palladium.util.annotation.Listener;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@ClassInfo(
        description = "Used by the Auto-Disconnect module.",
        authors = "cubert3d",
        date = "3/14/2021",
        type = ClassType.CALLBACK
)

@CallbackInfo(
        returns = Void.class,
        listeners = {
                @Listener(where = AutoDisconnectModule.class)
        },
        interactions = {
                @Interaction(where = ClientPlayNetworkHandlerMixin.class, method = "onHealthUpdateInject")
        }
)

public interface HealthUpdateCallback {

    Event<HealthUpdateCallback> EVENT = EventFactory.createArrayBacked(HealthUpdateCallback.class,
            (listeners) -> (health) -> {
                for (HealthUpdateCallback listener : listeners) {
                    listener.interact(health);
                }
            });

    void interact(float health);
}
