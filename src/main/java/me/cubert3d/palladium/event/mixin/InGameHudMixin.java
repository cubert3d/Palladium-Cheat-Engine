package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.event.callback.OverlayCallback;
import me.cubert3d.palladium.gui.ClickGUI;
import me.cubert3d.palladium.gui.TextHudRenderer;
import me.cubert3d.palladium.gui.widget.Widget;
import me.cubert3d.palladium.gui.widget.WidgetManager;
import me.cubert3d.palladium.module.modules.render.AntiOverlayModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/9/2021"
)

@Mixin(InGameHud.class)
abstract class InGameHudMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrices, float tickDelta, CallbackInfo info) {
        if (TextHudRenderer.shouldRender()) {
            TextHudRenderer.render(matrices);
        }
        for (Widget widget : WidgetManager.getWidgets()) {
            if (widget.shouldRender())
                widget.render(matrices);
        }
    }

    @Inject(method = "renderPumpkinOverlay()V",
            at = @At("HEAD"), cancellable = true)
    private void onRenderPumpkinOverlay(CallbackInfo info) {

        ActionResult result = OverlayCallback.EVENT.invoker().interact(AntiOverlayModule.Overlay.PUMPKIN);

        if (result.equals(ActionResult.FAIL))
            info.cancel();
    }

    @Inject(method = "renderPortalOverlay(F)V",
            at = @At("HEAD"), cancellable = true)
    private void onRenderPortalOverlay(float nauseaStrength, CallbackInfo info) {

        ActionResult result = OverlayCallback.EVENT.invoker().interact(AntiOverlayModule.Overlay.PORTAL);

        if (result.equals(ActionResult.FAIL))
            info.cancel();
    }
}
