package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/3/2021",
        status = "complete"
)

public interface PlayerJumpCallback {

    Event<PlayerJumpCallback> EVENT = EventFactory.createArrayBacked(PlayerJumpCallback.class,
            (listeners) -> (player) -> {
                for (PlayerJumpCallback listener : listeners) {
                    ActionResult result = listener.interact(player);

                    if (result != ActionResult.PASS)
                        return result;
                }
                return ActionResult.PASS;
            });

    ActionResult interact(PlayerEntity player);
}
