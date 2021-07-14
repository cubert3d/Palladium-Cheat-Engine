package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

@ClassInfo(
        authors = "cubert3d",
        date = "3/7/2021",
        type = ClassType.CALLBACK
)

public interface MineBlockCallback {
    Event<MineBlockCallback> EVENT = EventFactory.createArrayBacked(MineBlockCallback.class,
            (listeners) -> (player, stack) -> {
                for (MineBlockCallback listener : listeners) {
                    ActionResult result = listener.interact(player, stack);

                    if (result != ActionResult.PASS)
                        return result;
                }
                return ActionResult.PASS;
            });

    ActionResult interact(PlayerEntity player, ItemStack stack);
}
