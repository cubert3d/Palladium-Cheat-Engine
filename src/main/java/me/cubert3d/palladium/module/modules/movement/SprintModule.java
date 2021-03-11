package me.cubert3d.palladium.module.modules.movement;

import me.cubert3d.palladium.module.AbstractModule;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;

// Written by cubert3d on 3/10/2021

public final class SprintModule extends AbstractModule {
    public SprintModule() {
        super("Sprint", "Makes the player sprint.", ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }
}
