package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.event.mixin.mixins.ChatHudMixin;
import me.cubert3d.palladium.module.modules.player.ChatFilterModule;
import me.cubert3d.palladium.util.annotation.CallbackInfo;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.annotation.Interaction;
import me.cubert3d.palladium.util.annotation.Listener;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

@ClassInfo(
        authors = "cubert3d",
        date = "4/8/2021",
        type = ClassType.CALLBACK
)

@CallbackInfo(
        listeners = {
                @Listener(where = ChatFilterModule.class)
        },
        interactions = {
                @Interaction(where = ChatHudMixin.class, method = "addMessageInject")
        }
)

public interface ChatFilterCallback {

    Event<ChatFilterCallback> EVENT = EventFactory.createArrayBacked(ChatFilterCallback.class,
            (listeners) -> (message) -> {
                for (ChatFilterCallback listener : listeners) {
                    ActionResult result = listener.interact(message);

                    if (result != ActionResult.PASS)
                        return result;
                }

                return ActionResult.PASS;
            });

    ActionResult interact(String message);
}
