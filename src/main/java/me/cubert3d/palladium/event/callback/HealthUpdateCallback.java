package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/14/2021",
        status = "complete"
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
