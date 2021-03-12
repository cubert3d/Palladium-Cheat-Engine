package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/11/2021",
        status = "in-progress"
)

public interface MouseCallback {
    Event<MouseCallback> EVENT = EventFactory.createArrayBacked(MouseCallback.class,
            (listeners) -> () -> {
                for (MouseCallback listener : listeners) {
                    ActionResult result = listener.interact();

                    if (result != ActionResult.PASS)
                        return result;
                }
                return ActionResult.PASS;
            });

    ActionResult interact();
}
