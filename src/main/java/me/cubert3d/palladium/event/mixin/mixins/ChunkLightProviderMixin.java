package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.module.modules.render.XRayModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import net.minecraft.world.chunk.light.ChunkLightingView;
import net.minecraft.world.chunk.light.LevelPropagator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClassInfo(
        authors = "cubert3d",
        date = "3/24/2021",
        type = ClassType.MIXIN
)

@Mixin(ChunkLightProvider.class)
abstract class ChunkLightProviderMixin extends LevelPropagator implements ChunkLightingView {

    private ChunkLightProviderMixin(int levelCount, int expectedLevelSize, int expectedTotalSize) {
        super(levelCount, expectedLevelSize, expectedTotalSize);
    }

    @Inject(method = "getLightLevel(" +
            "Lnet/minecraft/util/math/BlockPos;" +
            ")I",
            at = @At("INVOKE"), cancellable = true)
    private void getLightLevelInject(BlockPos pos, final CallbackInfoReturnable<Integer> info) {
        if (Palladium.getInstance().getModuleManager().isModuleEnabled(XRayModule.class)) {
            info.setReturnValue(15);
        }
    }
}
