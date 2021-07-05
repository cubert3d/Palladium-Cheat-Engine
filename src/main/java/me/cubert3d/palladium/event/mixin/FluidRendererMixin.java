package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.modules.render.XRayModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/24/2021"
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
    private void onRender(BlockRenderView world, BlockPos pos, VertexConsumer vertexConsumer, FluidState state,
                          CallbackInfoReturnable<Boolean> info) {
        // Helps the X-Ray module handle fluid blocks in addition to solid blocks.
        if (ModuleManager.isModuleEnabled(XRayModule.class)
                && XRayModule.isSeeThrough(state.getBlockState().getBlock())) {
            info.setReturnValue(false);
        }
    }
}
