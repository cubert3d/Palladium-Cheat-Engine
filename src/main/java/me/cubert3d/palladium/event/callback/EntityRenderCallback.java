package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.event.mixin.mixins.EntityRenderDispatcherMixin;
import me.cubert3d.palladium.module.modules.render.ESPModule;
import me.cubert3d.palladium.util.annotation.CallbackInfo;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.annotation.Interaction;
import me.cubert3d.palladium.util.annotation.Listener;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

@ClassInfo(
        description = "Used for the ESP module.",
        authors = "cubert3d",
        date = "7/7/2021",
        type = ClassType.CALLBACK
)

@CallbackInfo(
        listeners = {
                @Listener(where = ESPModule.class)
        },
        interactions = {
                @Interaction(where = EntityRenderDispatcherMixin.class, method = "renderInject")
        }
)

public interface EntityRenderCallback {

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
