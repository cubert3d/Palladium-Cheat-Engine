package me.cubert3d.palladium.event.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public interface FreecamCallback {
    /*
    Used by the freecam module.
     */
    Event<FreecamCallback > EVENT = EventFactory.createArrayBacked(FreecamCallback .class,
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
