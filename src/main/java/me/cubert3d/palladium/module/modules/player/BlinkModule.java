package me.cubert3d.palladium.module.modules.player;

import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "3/10/2021",
        type = ClassType.MODULE
)

public final class BlinkModule extends ToggleModule {
    public BlinkModule() {
        super("Blink", "Blocks communication with the server.");
    }
}
