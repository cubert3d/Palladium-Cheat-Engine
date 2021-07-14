package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "4/10/2021",
        type = ClassType.MODULE
)

public final class PalladiumHudModule extends ToggleModule {
    public PalladiumHudModule() {
        super("HUD", "Toggles the HUD for Palladium Cheat Engine.");
    }
}
