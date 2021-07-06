package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.util.ActionResult;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "7/6/2021"
)

public interface BlockRenderCallback {

    /*
    Used for the X-Ray, where only the Block object is needed.
     */

    Event<BlockRenderCallback> EVENT = EventFactory.createArrayBacked(BlockRenderCallback.class,
            listeners -> block -> {
                for (BlockRenderCallback listener : listeners) {
                    ActionResult result = listener.interact(block);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact(Block block);
}
