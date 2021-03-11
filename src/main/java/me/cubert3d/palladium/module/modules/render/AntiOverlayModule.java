package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.module.AbstractModule;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;

// Written by cubert3d on 3/10/2021

public final class AntiOverlayModule extends AbstractModule {
    public AntiOverlayModule() {
        super("AntiOverlay", "Removes the pumpkin overlay.", ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
    }
}
