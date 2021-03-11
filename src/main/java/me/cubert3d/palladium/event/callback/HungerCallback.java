package me.cubert3d.palladium.event.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

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
