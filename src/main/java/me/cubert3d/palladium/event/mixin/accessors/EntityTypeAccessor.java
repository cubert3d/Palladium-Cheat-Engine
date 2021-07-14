package me.cubert3d.palladium.event.mixin.accessors;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@ClassInfo(
        authors = "cubert3d",
        date = "6/26/2021",
        type = ClassType.MIXIN
)

@Mixin(EntityType.class)
public interface EntityTypeAccessor {
    @Invoker("register")
    static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        throw new AssertionError();
    }
}
