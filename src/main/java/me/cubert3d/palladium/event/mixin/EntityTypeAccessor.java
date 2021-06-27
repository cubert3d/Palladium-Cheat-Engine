package me.cubert3d.palladium.event.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityType.class)
public interface EntityTypeAccessor {
    @Invoker("register")
    public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        throw new AssertionError();
    }
}
