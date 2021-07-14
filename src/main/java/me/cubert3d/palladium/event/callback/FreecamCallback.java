package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

@ClassInfo(
        authors = "cubert3d",
        date = "7/13/2021",
        type = ClassType.CALLBACK
)

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
