package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.event.callback.BlockRenderCallback;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClassInfo(
        authors = "cubert3d",
        date = "3/10/2021",
        type = ClassType.MIXIN
)

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock implements ItemConvertible {

    private BlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "shouldDrawSide(" +
            "Lnet/minecraft/block/BlockState;" +    // BlockState state
            "Lnet/minecraft/world/BlockView;" +     // BlockView world
            "Lnet/minecraft/util/math/BlockPos;" +  // BlockPos pos
            "Lnet/minecraft/util/math/Direction;" + // Direction facing
            ")Z",                                   // returns boolean
            at = @At(value = "RETURN"),
            cancellable = true)
    private static void shouldDrawSideInject(BlockState state, BlockView world, BlockPos pos, Direction facing,
                                             final CallbackInfoReturnable<Boolean> info) {
        /*
         Basic X-Ray method. Makes whitelisted blocks visible, and non-whitelisted blocks invisible.
         However, this mixin is not enough--see AbstractBlockStateMixin, BlockEntityRenderDispatcherMixin,
         ChunkLightProviderMixin, and FluidRendererMixin.
        */
        boolean render = BlockRenderCallback.EVENT.invoker().shouldRender(state.getBlock());
        info.setReturnValue(render);
    }
}
