package me.cubert3d.palladium.module.modules.movement;

import me.cubert3d.palladium.module.Module;
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

public final class SprintModule extends Module {
    public SprintModule() {
        super("Sprint", "Makes the player sprint.", ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }
}
