package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.event.mixin.mixins.BlockMixin;
import me.cubert3d.palladium.module.modules.render.XRayModule;
import me.cubert3d.palladium.util.annotation.CallbackInfo;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.annotation.Interaction;
import me.cubert3d.palladium.util.annotation.Listener;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

@ClassInfo(
        description = "Used for the X-Ray, where more info is needed.",
        authors = "cubert3d",
        date = "7/6/2021",
        type = ClassType.CALLBACK
)

@CallbackInfo(
        listeners = {
                @Listener(where = XRayModule.class)
        },
        interactions = {
                @Interaction(where = BlockMixin.class, method = "shouldDrawSideInject")
        }
)

public interface BlockStateRenderCallback {

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
