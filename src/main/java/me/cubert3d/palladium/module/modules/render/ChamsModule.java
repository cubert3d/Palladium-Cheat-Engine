package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.modules.ToggleModule;

public final class ChamsModule extends ToggleModule {
    public ChamsModule() {
        super("Chams", "Makes entities render through walls.", ModuleDevStatus.DEBUG_ONLY);
    }
}
