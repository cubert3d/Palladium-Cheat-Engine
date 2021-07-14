package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

@ClassInfo(
        authors = "cubert3d",
        date = "7/7/2021",
        type = ClassType.CALLBACK
)

public interface EntityRenderCallback {
    /*
    Used for the ESP module.
     */
    Event<EntityRenderCallback> EVENT = EventFactory.createArrayBacked(EntityRenderCallback.class,
            listeners -> entity -> {
                for (EntityRenderCallback listener : listeners) {
                    ActionResult result = listener.interact(entity);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact(Entity entity);
}
