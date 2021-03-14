package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/10/2021",
        status = "complete"
)

public final class AntiOverlayModule extends Module {
    public AntiOverlayModule() {
        super("AntiOverlay", "Removes the pumpkin overlay.", ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
    }
}
