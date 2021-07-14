package me.cubert3d.palladium.util.exception;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "7/5/2021",
        type = ClassType.EXCEPTION
)

public final class ModuleNotFoundException extends RuntimeException {

    public ModuleNotFoundException() {

    }

    public ModuleNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
