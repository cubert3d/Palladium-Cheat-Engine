package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "6/22/2021",
        type = ClassType.MODULE
)

public final class ChamsModule extends ToggleModule {
    public ChamsModule() {
        super("Chams", "Makes entities render through walls.");
    }
}
