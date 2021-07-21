package me.cubert3d.palladium.event.mixin.mixins;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.callback.BlockRenderCallback;
import me.cubert3d.palladium.event.mixin.MixinCaster;
import me.cubert3d.palladium.module.modules.render.XRayModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.state.State;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClassInfo(
        authors = "cubert3d",
        date = "3/24/2021",
        type = ClassType.MIXIN
)

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin extends State<Block, BlockState> implements MixinCaster<AbstractBlock.AbstractBlockState> {

    private AbstractBlockStateMixin(Block owner, ImmutableMap<Property<?>, Comparable<?>> entries, MapCodec<BlockState> codec) {
        super(owner, entries, codec);
    }

    @Inject(method = "getAmbientOcclusionLightLevel(" +
            "Lnet/minecraft/world/BlockView;" +
            "Lnet/minecraft/util/math/BlockPos;" +
            ")F",
            at = @At(value = "INVOKE"),
            cancellable = true)
    private void getAmbientOcclusionLightLevelInject(BlockView world, BlockPos pos,
                                                     final CallbackInfoReturnable<Float> info) {
        /*
         Turns off all smooth lighting when X-Ray is enabled, because it
         would make all whitelisted blocks really dark, even with their
         light level set to max, if smooth lighting is enabled.
        */
        if (Palladium.getInstance().getModuleManager().isModuleEnabled(XRayModule.class)) {
            info.setReturnValue(1F);
        }
    }

    @Inject(method = "getRenderType()Lnet/minecraft/block/BlockRenderType;",
            at = @At(value = "INVOKE"),
            cancellable = true)
    private void getRenderTypeInject(final CallbackInfoReturnable<BlockRenderType> info) {
        /*
         When X-Ray is enabled, this overrides all non-whitelisted block
         models to being INVISIBLE: this is because merely cancelling the
         shouldDrawSide method in the Block class is not enough for blocks
         which are not full blocks, such as stairs, or flowers.
        */
        boolean render = BlockRenderCallback.EVENT.invoker().shouldRender(self().getBlock());
        if (!render) {
            info.setReturnValue(BlockRenderType.INVISIBLE);
        }
    }
}
