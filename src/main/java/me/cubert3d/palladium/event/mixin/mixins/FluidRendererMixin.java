package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.event.callback.BlockRenderCallback;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClassInfo(
        authors = "cubert3d",
        date = "3/24/2021",
        type = ClassType.MIXIN
)

@Mixin(FluidRenderer.class)
abstract class FluidRendererMixin {

    @Inject(method = "render(" +
            "Lnet/minecraft/world/BlockRenderView;" +
            "Lnet/minecraft/util/math/BlockPos;" +
            "Lnet/minecraft/client/render/VertexConsumer;" +
            "Lnet/minecraft/fluid/FluidState;" +
            ")Z",
            at = @At("INVOKE"),
            cancellable = true)
    private void renderInject(BlockRenderView world, BlockPos pos, VertexConsumer vertexConsumer, FluidState state,
                              final CallbackInfoReturnable<Boolean> info) {
        // Helps the X-Ray module handle fluid blocks in addition to solid blocks.
        ActionResult result = BlockRenderCallback.EVENT.invoker().interact(state.getBlockState().getBlock());

        if (result.equals(ActionResult.FAIL)) {
            info.setReturnValue(false);
        }
    }
}
