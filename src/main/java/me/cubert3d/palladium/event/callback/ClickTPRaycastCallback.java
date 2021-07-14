package me.cubert3d.palladium.event.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public interface ClickTPRaycastCallback {
    /*
    Used by the ClickTP module, for checking the block that the player is looking at.
     */
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
