package me.cubert3d.palladium.event.mixin.accessors;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@ClassInfo(
        authors = "cubert3d",
        date = "6/22/2021",
        type = ClassType.MIXIN
)

@Mixin(KeyBinding.class)
public interface KeyBindingAccessor {
    @Accessor
    InputUtil.Key getBoundKey();
}
