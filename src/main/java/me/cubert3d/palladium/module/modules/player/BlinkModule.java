package me.cubert3d.palladium.module.modules.player;

import me.cubert3d.palladium.module.AbstractModule;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;

public final class BlinkModule extends AbstractModule {
    public BlinkModule() {
        super("Blink", "Temporarily blocks communication with the server.", ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
    }
}
