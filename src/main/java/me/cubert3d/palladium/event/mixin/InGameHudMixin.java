package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.module.ModuleList;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/9/2021",
        status = "complete"
)

@Mixin(InGameHud.class)
public final class InGameHudMixin {
    @Redirect(method = "render",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/client/gui/hud/InGameHud;renderPumpkinOverlay()V"))
    private void renderPumpkinOverlayRedirect(InGameHud hud) {
        if (!ModuleList.getModule("AntiOverlay").get().isEnabled())
            ((InGameHudInvoker) hud).invokeRenderPumpkinOverlay();
    }
}
