package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.modules.render.XRayModule;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockRenderManager.class)
public final class BlockRenderManagerMixin {



    // Doesn't seem to do anything
    @Inject(method = "renderBlockAsEntity(" +
            "Lnet/minecraft/block/BlockState;" +
            "Lnet/minecraft/client/util/math/MatrixStack;" +
            "Lnet/minecraft/client/render/VertexConsumerProvider;" +
            "II" +
            ")V",
            at = @At("INVOKE"),
            cancellable = true)
    private void onRenderBlockAsEntity(BlockState state, MatrixStack matrices, VertexConsumerProvider vertexConsumer,
                                       int light, int overlay, CallbackInfo info) {
        System.out.println("onRenderBlockAsEntity: " + state.getBlock().getName().getString());
        /*
        if (ModuleManager.isModuleEnabled(XRayModule.class) && XRayModule.isSeeThrough(state.getBlock()))
            info.cancel();

         */
    }
}
