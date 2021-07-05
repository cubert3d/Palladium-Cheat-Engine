package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.module.modules.ToggleModule;

public final class TooltipsModule extends ToggleModule {
    public TooltipsModule() {
        super("Tooltips", "Displays extra information in item tooltips.", ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
    }
}
