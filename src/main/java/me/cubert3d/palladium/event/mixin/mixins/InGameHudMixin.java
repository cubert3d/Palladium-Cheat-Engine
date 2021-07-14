package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.callback.OverlayCallback;
import me.cubert3d.palladium.module.modules.render.AntiOverlayModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassInfo(
        authors = "cubert3d",
        date = "3/9/2021",
        type = ClassType.MIXIN
)

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {

    @Inject(method = "render", at = @At("TAIL"))
    private void renderInject(MatrixStack matrices, float tickDelta, final CallbackInfo info) {
        Palladium.getInstance().getGuiRenderer().render(matrices);
    }

    @Inject(method = "renderPumpkinOverlay()V",
            at = @At("HEAD"), cancellable = true)
    private void renderPumpkinOverlayInject(final CallbackInfo info) {

        ActionResult result = OverlayCallback.EVENT.invoker().interact(AntiOverlayModule.Overlay.PUMPKIN);

        if (result.equals(ActionResult.FAIL))
            info.cancel();
    }

    @Inject(method = "renderPortalOverlay(F)V",
            at = @At("HEAD"), cancellable = true)
    private void renderPortalOverlayInject(float nauseaStrength, final CallbackInfo info) {

        ActionResult result = OverlayCallback.EVENT.invoker().interact(AntiOverlayModule.Overlay.PORTAL);

        if (result.equals(ActionResult.FAIL))
            info.cancel();
    }
}
