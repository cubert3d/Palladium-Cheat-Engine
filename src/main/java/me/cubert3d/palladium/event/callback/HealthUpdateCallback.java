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
import net.minecraft.util.ActionResult;

@ClassInfo(
        description = "Used by the Auto-Disconnect module.",
        authors = "cubert3d",
        date = "3/14/2021",
        type = ClassType.CALLBACK
)

@CallbackInfo(
        listeners = {
                @Listener(where = AutoDisconnectModule.class, cancels = false)
        },
        interactions = {
                @Interaction(where = ClientPlayNetworkHandlerMixin.class, method = "onHealthUpdateInject", cancels = false)
        }
)

public interface HealthUpdateCallback {

    Event<HealthUpdateCallback> EVENT = EventFactory.createArrayBacked(HealthUpdateCallback.class,
            (listeners) -> (health) -> {
                for (HealthUpdateCallback listener : listeners) {
                    ActionResult result = listener.interact(health);
                    if (result != ActionResult.PASS)
                        return result;
                }
                return ActionResult.PASS;
            });

    ActionResult interact(float health);
}
