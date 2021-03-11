package me.cubert3d.palladium.module.modules.movement;

import me.cubert3d.palladium.module.AbstractModule;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;

// Written by cubert3d on 3/10/2021

public final class SneakModule extends AbstractModule {
    public SneakModule() {
        super("Sneak", "Makes the player sneak.", ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }
}
