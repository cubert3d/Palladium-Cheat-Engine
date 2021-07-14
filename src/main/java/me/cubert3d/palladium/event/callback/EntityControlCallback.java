package me.cubert3d.palladium.event.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public interface EntityControlCallback {
    /*
    Used by the entity control module.
     */
    Event<EntityControlCallback> EVENT = EventFactory.createArrayBacked(EntityControlCallback.class,
            listeners -> () -> {
                for (EntityControlCallback listener : listeners) {
                    ActionResult result = listener.interact();
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact();
}
