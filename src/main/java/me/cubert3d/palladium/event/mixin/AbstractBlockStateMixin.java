package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.modules.render.XRayModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.state.State;
import net.minecraft.util.math.BlockPos;
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

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {

    @Inject(method = "getAmbientOcclusionLightLevel(" +
            "Lnet/minecraft/world/BlockView;" +
            "Lnet/minecraft/util/math/BlockPos;" +
            ")F",
            at = @At(value = "INVOKE"),
            cancellable = true)
    private void onGetAmbientOcclusionLightLevel(BlockView world, BlockPos pos,
                                                 CallbackInfoReturnable<Float> info) {
        if (ModuleManager.isModuleEnabled(XRayModule.class))
            info.setReturnValue(1F);
    }

    @Inject(method = "getRenderType()Lnet/minecraft/block/BlockRenderType;",
            at = @At(value = "INVOKE"),
            cancellable = true)
    private void onGetRenderType(CallbackInfoReturnable<BlockRenderType> info) {
        Block thisBlock = ((BlockState) (Object) this).getBlock();
        if (ModuleManager.isModuleEnabled(XRayModule.class)
                && XRayModule.isSeeThrough(thisBlock)) {
            info.setReturnValue(BlockRenderType.INVISIBLE);
        }
    }
}
