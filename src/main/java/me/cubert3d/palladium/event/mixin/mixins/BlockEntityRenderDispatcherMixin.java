package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.event.callback.BlockRenderCallback;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassInfo(
        authors = "cubert3d",
        date = "4/7/2021",
        type = ClassType.MIXIN
)

@Mixin(BlockEntityRenderDispatcher.class)
public abstract class BlockEntityRenderDispatcherMixin {

    @Inject(method = "render(" +
            "Lnet/minecraft/block/entity/BlockEntity;" +
            "F" +
            "Lnet/minecraft/client/util/math/MatrixStack;" +
            "Lnet/minecraft/client/render/VertexConsumerProvider;" +
            ")V", at = @At("HEAD"), cancellable = true)
    private <E extends BlockEntity> void renderInject(E blockEntity, float tickDelta, MatrixStack matrix,
                                                      VertexConsumerProvider vertexConsumerProvider,
                                                      final CallbackInfo info) {
        // For x-ray, prevents block entities that are not on the whitelist from rendering.
        if (blockEntity.hasWorld()) {
            Block block = blockEntity.getWorld().getBlockState(blockEntity.getPos()).getBlock();
            boolean render = BlockRenderCallback.EVENT.invoker().shouldRender(block);
            if (!render) {
                info.cancel();
            }
        }
    }
}
