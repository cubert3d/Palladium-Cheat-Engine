package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "6/23/2021",
        status = "in-progress"
)

public final class ESPModule extends ToggleModule {
    public ESPModule() {
        super("ESP", "Renders a box around entities that can be seen through walls.", ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
    }
}
