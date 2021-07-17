package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.event.mixin.mixins.KeyBindingMixin;
import me.cubert3d.palladium.module.modules.movement.AutoWalkModule;
import me.cubert3d.palladium.module.modules.movement.SprintModule;
import me.cubert3d.palladium.util.annotation.CallbackInfo;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.annotation.Interaction;
import me.cubert3d.palladium.util.annotation.Listener;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.util.ActionResult;

@ClassInfo(
        authors = "cubert3d",
        date = "7/16/2021",
        type = ClassType.CALLBACK
)

@CallbackInfo(
        listeners = {
                @Listener(where = AutoWalkModule.class),
                @Listener(where = SprintModule.class)
        },
        interactions = {
                @Interaction(where = KeyBindingMixin.class, method = "isPressedInject")
        }
)

public interface KeyPressedCallback {

    Event<KeyPressedCallback> EVENT = EventFactory.createArrayBacked(KeyPressedCallback.class,
            listeners -> binding -> {
                for (KeyPressedCallback listener : listeners) {
                    ActionResult result = listener.interact(binding);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact(KeyBinding binding);
}
