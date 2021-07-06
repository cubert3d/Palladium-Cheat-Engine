package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.module.modules.render.XRayModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.light.ChunkLightProvider;
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

@Mixin(ChunkLightProvider.class)
abstract class ChunkLightProviderMixin {
    @Inject(method = "getLightLevel(" +
            "Lnet/minecraft/util/math/BlockPos;" +
            ")I",
            at = @At("INVOKE"), cancellable = true)
    private void onGetLightLevel(BlockPos pos, CallbackInfoReturnable<Integer> info) {
        if (Palladium.getInstance().getModuleManager().isModuleEnabled(XRayModule.class))
            info.setReturnValue(15);
    }
}
