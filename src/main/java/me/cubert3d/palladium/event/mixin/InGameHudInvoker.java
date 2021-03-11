package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/9/2021",
        status = "complete"
)

@Mixin(InGameHud.class)
public interface InGameHudInvoker {
    @Invoker void invokeRenderPumpkinOverlay();
}
