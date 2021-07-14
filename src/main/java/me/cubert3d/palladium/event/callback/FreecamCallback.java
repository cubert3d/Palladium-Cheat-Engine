package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.event.mixin.mixins.ClientPlayNetworkHandlerMixin;
import me.cubert3d.palladium.module.modules.render.FreecamModule;
import me.cubert3d.palladium.util.annotation.CallbackInfo;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.annotation.Interaction;
import me.cubert3d.palladium.util.annotation.Listener;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

@ClassInfo(
        description = "Used by the freecam module.",
        authors = "cubert3d",
        date = "7/13/2021",
        type = ClassType.CALLBACK
)

@CallbackInfo(
        listeners = {
                @Listener(where = FreecamModule.class)
        },
        interactions = {
                @Interaction(where = ClientPlayNetworkHandlerMixin.class, method = "onSendPacket")
        }
)

public interface FreecamCallback {

    Event<FreecamCallback > EVENT = EventFactory.createArrayBacked(FreecamCallback.class,
            listeners -> () -> {
                for (FreecamCallback listener : listeners) {
                    ActionResult result = listener.interact();
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact();
}
