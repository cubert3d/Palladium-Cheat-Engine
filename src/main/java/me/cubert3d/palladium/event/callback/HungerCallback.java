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
        date = "3/4/2021",
        status = "complete"
)

public interface HungerCallback {

    Event<HungerCallback> EVENT = EventFactory.createArrayBacked(HungerCallback.class,
            (listeners) -> (player, hunger) -> {
                for (HungerCallback listener : listeners) {
                    ActionResult result = listener.interact(player, hunger);

                    if (result != ActionResult.PASS)
                        return result;
                }

                return ActionResult.PASS;
            });

    ActionResult interact(PlayerEntity player, int hunger);
}
