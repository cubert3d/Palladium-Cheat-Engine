package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.module.ModuleList;
import me.cubert3d.palladium.util.annotation.DebugOnly;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import net.minecraft.world.chunk.light.LightingProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@DebugOnly
@Mixin(ChunkLightProvider.class)
public final class ChunkLightProviderMixin {
    @Inject(method = "getLightLevel(Lnet/minecraft/util/math/BlockPos;)I",
            at = @At(value = "HEAD"),
            cancellable = true)
    private void renderPumpkinOverlayRedirect(BlockPos blockPos, CallbackInfoReturnable<Integer> info) {
    }

    /*
    @Mixin(ChunkLightProvider.class)

    @Inject(method = "getLightLevel(Lnet/minecraft/util/math/BlockPos;)I",
            at = @At(value = "HEAD"),
            cancellable = true)
    private void renderPumpkinOverlayRedirect(BlockPos blockPos, CallbackInfoReturnable<Integer> info) {
        if (ModuleList.getModule("FullBright").isEnabled())
            info.setReturnValue(15);
    }
     */

    /*
    @Mixin(BlockRenderView.class)

    @Inject(method = "getLightLevel(Lnet/minecraft/world/LightType;Lnet/minecraft/util/math/BlockPos;)I",
            at = @At(value = "HEAD"),
            cancellable = true)
    private void onGetLightLevel(LightType type, BlockPos pos, CallbackInfoReturnable<Integer> info) {
        if (ModuleList.getModule("FullBright").isEnabled())
            info.setReturnValue(15);
    }
     */
}
