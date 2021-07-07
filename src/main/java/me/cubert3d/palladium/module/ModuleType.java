package me.cubert3d.palladium.module;

import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/5/2021",
        status = "complete"
)

public enum ModuleType {
    TOGGLE,         // This module can be turned on and off
    COMMAND         // This module is executed once, like a command
}
