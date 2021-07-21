package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.event.mixin.mixins.HorseBaseEntityMixin;
import me.cubert3d.palladium.module.modules.movement.EntityControlModule;
import me.cubert3d.palladium.util.annotation.CallbackInfo;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.annotation.Interaction;
import me.cubert3d.palladium.util.annotation.Listener;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@ClassInfo(
        description = "Used by the entity control module.",
        authors = "cubert3d",
        date = "7/9/2021",
        type = ClassType.CALLBACK
)

@CallbackInfo(
        returns = Boolean.class,
        listeners = {
                @Listener(where = EntityControlModule.class)
        },
        interactions = {
                @Interaction(where = HorseBaseEntityMixin.class, method = {"isTameInject", "isSaddledRedirect", "canJumpInject"})
        }
)

public interface EntityControlCallback {

    Event<EntityControlCallback> EVENT = EventFactory.createArrayBacked(EntityControlCallback.class,
            listeners -> () -> {
                for (EntityControlCallback listener : listeners) {
                    boolean control = listener.shouldControl();
                    if (control) {
                        return true;
                    }
                }
                return false;
            });

    boolean shouldControl();
}
