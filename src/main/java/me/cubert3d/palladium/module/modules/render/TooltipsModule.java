package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "7/4/2021",
        type = ClassType.MODULE
)

public final class TooltipsModule extends ToggleModule {
    public TooltipsModule() {
        super("Tooltips", "Displays extra information in item tooltips.");
    }
}
