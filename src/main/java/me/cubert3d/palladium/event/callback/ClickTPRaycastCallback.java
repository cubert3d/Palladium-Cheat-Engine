package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.event.mixin.mixins.ClientPlayerEntityMixin;
import me.cubert3d.palladium.module.modules.movement.ClickTPModule;
import me.cubert3d.palladium.util.annotation.CallbackInfo;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.annotation.Interaction;
import me.cubert3d.palladium.util.annotation.Listener;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

@ClassInfo(
        description = "Used by the ClickTP module, for checking the block that the player is looking at.",
        authors = "cubert3d",
        date = "7/13/2021",
        type = ClassType.CALLBACK
)

@CallbackInfo(
        listeners = {
                @Listener(where = ClickTPModule.class, cancels = false)
        },
        interactions = {
                @Interaction(where = ClientPlayerEntityMixin.class, method = "swingHandInject", cancels = false)
        }
)

public interface ClickTPRaycastCallback {

    Event<ClickTPRaycastCallback> EVENT = EventFactory.createArrayBacked(ClickTPRaycastCallback.class,
            listeners -> hand -> {
                for (ClickTPRaycastCallback listener : listeners) {
                    ActionResult result = listener.interact(hand);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact(Hand hand);
}
