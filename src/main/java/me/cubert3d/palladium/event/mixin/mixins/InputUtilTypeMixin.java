package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassInfo(
        authors = "cubert3d",
        date = "7/4/2021",
        type = ClassType.MIXIN
)

@Mixin(InputUtil.Type.class)
abstract class InputUtilTypeMixin {

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void staticConstructorInject(final CallbackInfo info) {
        // The config loading is called here to ensure that it is only read *after* the keys are loaded.
        Palladium.getInstance().getConfiguration().loadConfig();
    }
}
