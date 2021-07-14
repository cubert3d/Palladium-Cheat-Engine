package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.event.mixin.mixins.AbstractBlockStateMixin;
import me.cubert3d.palladium.event.mixin.mixins.BlockEntityRenderDispatcherMixin;
import me.cubert3d.palladium.event.mixin.mixins.FluidRendererMixin;
import me.cubert3d.palladium.module.modules.render.XRayModule;
import me.cubert3d.palladium.util.annotation.CallbackInfo;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.annotation.Interaction;
import me.cubert3d.palladium.util.annotation.Listener;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.util.ActionResult;

@ClassInfo(
        description = "Used for the X-Ray, where only the Block object is needed.",
        authors = "cubert3d",
        date = "7/6/2021",
        type = ClassType.CALLBACK
)

@CallbackInfo(
        listeners = @Listener(where = XRayModule.class),
        interactions = {
                @Interaction(where = AbstractBlockStateMixin.class, method = "getRenderTypeInject"),
                @Interaction(where = BlockEntityRenderDispatcherMixin.class, method = "renderInject"),
                @Interaction(where = FluidRendererMixin.class, method = "renderInject")
        }
)

public interface BlockRenderCallback {
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
