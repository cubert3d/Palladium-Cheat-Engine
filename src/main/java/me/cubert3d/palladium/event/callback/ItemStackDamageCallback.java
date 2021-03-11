package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import me.cubert3d.palladium.util.annotation.DebugOnly;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/8/2021",
        status = "complete"
)

@DebugOnly
public interface ItemStackDamageCallback {
    Event<ItemStackDamageCallback> EVENT = EventFactory.createArrayBacked(ItemStackDamageCallback.class,
            (listeners) -> (player, stack) -> {
                for (ItemStackDamageCallback listener : listeners) {
                    ActionResult result = listener.interact(player, stack);

                    if (result != ActionResult.PASS)
                        return result;
                }
                return ActionResult.PASS;
            });

    ActionResult interact(PlayerEntity player, ItemStack stack);
}
