package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.ActionResult;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/2/2021",
        status = "complete"
)

public interface PlayerChatCallback {

    Event<PlayerChatCallback> EVENT = EventFactory.createArrayBacked(PlayerChatCallback.class,
            (listeners) -> (player, message) -> {
                for (PlayerChatCallback listener : listeners) {
                    ActionResult result = listener.interact(player, message);

                    if (result != ActionResult.PASS)
                        return result;
                }

                return ActionResult.PASS;
            });

    ActionResult interact(ClientPlayerEntity player, String message);
}
