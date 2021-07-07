package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/10/2021"
)

public final class PalladiumHudModule extends ToggleModule {
    public PalladiumHudModule() {
        super("HUD", "Toggles the HUD for Palladium Cheat Engine.", ModuleDevStatus.AVAILABLE);
    }
}
