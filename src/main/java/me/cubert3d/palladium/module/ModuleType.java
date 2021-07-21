package me.cubert3d.palladium.module;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        description = "Determines whether a module is toggleable, or is a command.",
        authors = "cubert3d",
        date = "3/5/2021",
        type = ClassType.MISC
)

public enum ModuleType {
    TOGGLE, // This module can be turned on and off
    COMMAND // This module is executed once, like a command
}
