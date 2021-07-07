package me.cubert3d.palladium.event.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

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
