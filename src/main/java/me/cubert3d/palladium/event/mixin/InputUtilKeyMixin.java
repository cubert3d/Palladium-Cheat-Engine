package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.input.Keys;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InputUtil.Key.class)
public final class InputUtilKeyMixin {
    @Inject(method = "<init>(Ljava/lang/String;Lnet/minecraft/client/util/InputUtil$Type;I)V",
            at = @At("TAIL"))
    private void onConstruct(String translationKey, InputUtil.Type type, int code, CallbackInfo info) {
        Keys.mapKey((InputUtil.Key) (Object) this);
    }
}
