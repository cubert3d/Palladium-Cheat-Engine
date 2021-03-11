package me.cubert3d.palladium.module.modules.movement;

import me.cubert3d.palladium.module.AbstractModule;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/10/2021",
        status = "complete"
)

public final class SneakModule extends AbstractModule {
    public SneakModule() {
        super("Sneak", "Makes the player sneak.", ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }
}
