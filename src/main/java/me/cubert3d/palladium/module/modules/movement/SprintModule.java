package me.cubert3d.palladium.module.modules.movement;

import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/10/2021"
)

public final class SprintModule extends ToggleModule {
    public SprintModule() {
        super("Sprint", "Makes the player sprint.");
    }
}
