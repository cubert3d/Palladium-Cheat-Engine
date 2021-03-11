package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.event.callback.DrawTextCallback;
import me.cubert3d.palladium.event.callback.HungerCallback;
import me.cubert3d.palladium.util.annotation.DebugOnly;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@DebugOnly
@Mixin(TextRenderer.class)
public class DrawTextMixin {
    @Inject(at = @At(value = "TAIL"),
            method = "draw(" +
                    "Lnet/minecraft/client/util/math/MatrixStack;" +    // MatrixStack matrices
                    "Ljava/lang/String;" +                              // String text
                    "F" +                                               // float x
                    "F" +                                               // float y
                    "I" +                                               // int color
                    ")I")                                               // returns int
    private void onDrawText(MatrixStack matrices, String text, float x, float y, int color,
                            CallbackInfoReturnable<Integer> info) {

        ActionResult result = DrawTextCallback.EVENT.invoker().interact(matrices, text, x, y, color);

        if (result.equals(ActionResult.FAIL))
            info.cancel();
    }
}
