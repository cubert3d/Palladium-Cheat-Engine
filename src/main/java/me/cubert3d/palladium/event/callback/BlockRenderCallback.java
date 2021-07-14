package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.util.ActionResult;

@ClassInfo(
        authors = "cubert3d",
        date = "7/6/2021",
        type = ClassType.CALLBACK
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
