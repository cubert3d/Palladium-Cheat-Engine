package me.cubert3d.palladium.event.mixin.accessors;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@ClassInfo(
        authors = "cubert3d",
        date = "7/18/2021",
        type = ClassType.MIXIN
)

@Mixin(World.class)
public interface WorldAccessor {
    @Accessor
    float getRainGradientPrev();
}
