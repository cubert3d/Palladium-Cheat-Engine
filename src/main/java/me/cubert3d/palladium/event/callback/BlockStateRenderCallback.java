package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "7/6/2021"
)

public interface BlockStateRenderCallback {

    /*
    Used for the X-Ray, where more info is needed.
     */

    Event<BlockStateRenderCallback> EVENT = EventFactory.createArrayBacked(BlockStateRenderCallback.class,
            listeners -> (state, view, pos, facing, returns) -> {
                for (BlockStateRenderCallback listener : listeners) {
                    ActionResult result = listener.interact(state, view, pos, facing, returns);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact(BlockState state, BlockView view, BlockPos pos, Direction facing, boolean returns);
}
