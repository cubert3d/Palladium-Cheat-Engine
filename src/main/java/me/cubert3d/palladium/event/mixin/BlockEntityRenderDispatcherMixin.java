package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.module.modules.render.XRayModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/7/2021"
)

@Mixin(BlockEntityRenderDispatcher.class)
abstract class BlockEntityRenderDispatcherMixin {
    @Inject(method = "render(" +
            "Lnet/minecraft/block/entity/BlockEntity;" +
            "F" +
            "Lnet/minecraft/client/util/math/MatrixStack;" +
            "Lnet/minecraft/client/render/VertexConsumerProvider;" +
            ")V", at = @At("HEAD"), cancellable = true)
    private <E extends BlockEntity> void onRender(E blockEntity, float tickDelta, MatrixStack matrix,
                                                  VertexConsumerProvider vertexConsumerProvider,
                                                  CallbackInfo info) {
        // For x-ray, prevents block entities that are not on the whitelist from rendering.
        if (blockEntity.hasWorld()) {
            Block block = blockEntity.getWorld().getBlockState(blockEntity.getPos()).getBlock();
            if (Palladium.getInstance().getModuleManager().isModuleEnabled(XRayModule.class) && XRayModule.isSeeThrough(block))
                info.cancel();
        }
    }
}
