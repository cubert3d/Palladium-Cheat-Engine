package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.module.ModuleList;
import me.cubert3d.palladium.util.annotation.DebugOnly;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.LightType;
import org.spongepowered.asm.mixin.Mixin;

@DebugOnly
@Mixin(BlockRenderView.class)
public interface MixinBlockRenderView extends BlockRenderView {
    @Override
    default int getLightLevel(LightType type, BlockPos pos) {
        if (ModuleList.getModule("FullBright").isEnabled())
            return 15;
        else
            return this.getLightingProvider().get(type).getLightLevel(pos);
    }
}
