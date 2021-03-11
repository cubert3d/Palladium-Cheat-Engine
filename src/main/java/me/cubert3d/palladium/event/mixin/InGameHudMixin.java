package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.module.ModuleList;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Redirect(method = "render",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/client/gui/hud/InGameHud;renderPumpkinOverlay()V"))
    private void renderPumpkinOverlayRedirect(InGameHud hud) {
        if (!ModuleList.getModule("AntiOverlay").isEnabled())
            ((InGameHudInvoker) hud).invokeRenderPumpkinOverlay();
    }
}
