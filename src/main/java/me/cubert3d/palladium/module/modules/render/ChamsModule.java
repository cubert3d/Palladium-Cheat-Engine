package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;

public final class ChamsModule extends Module {
    public ChamsModule() {
        super("Chams", "Makes entities render through walls.", ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }
}