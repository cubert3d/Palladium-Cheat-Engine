package me.cubert3d.palladium.module.modules.player;

import me.cubert3d.palladium.module.AbstractModule;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/10/2021",
        status = "complete"
)

public final class BlinkModule extends AbstractModule {
    public BlinkModule() {
        super("Blink", "Blocks communication with the server.", ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
    }
}
