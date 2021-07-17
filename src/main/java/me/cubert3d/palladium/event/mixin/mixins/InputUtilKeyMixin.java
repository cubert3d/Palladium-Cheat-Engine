package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.event.mixin.MixinCaster;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.input.Keys;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassInfo(
        authors = "cubert3d",
        date = "6/22/2021",
        type = ClassType.MIXIN
)

@Mixin(InputUtil.Key.class)
abstract class InputUtilKeyMixin implements MixinCaster<InputUtil.Key> {

    @Inject(method = "<init>(Ljava/lang/String;Lnet/minecraft/client/util/InputUtil$Type;I)V",
            at = @At("TAIL"))
    private void constructorInject(String translationKey, InputUtil.Type type, int code, final CallbackInfo info) {
        Keys.mapKey(self());
    }
}
