package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.modules.render.XRayModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/10/2021"
)

@Mixin(Block.class)
public final class BlockMixin {
    @Inject(method = "shouldDrawSide(" +
            "Lnet/minecraft/block/BlockState;" +    // BlockState state
            "Lnet/minecraft/world/BlockView;" +     // BlockView world
            "Lnet/minecraft/util/math/BlockPos;" +  // BlockPos pos
            "Lnet/minecraft/util/math/Direction;" + // Direction facing
            ")Z",                                   // returns boolean
            at = @At(value = "RETURN"),
            cancellable = true)
    private static void onShouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction facing,
                                         CallbackInfoReturnable<Boolean> info) {
        if (ModuleManager.isModuleEnabled(XRayModule.class)) {
            info.setReturnValue(XRayModule.modifyDrawSide(state, world, pos, facing, info.getReturnValueZ()));
        }
    }
}
