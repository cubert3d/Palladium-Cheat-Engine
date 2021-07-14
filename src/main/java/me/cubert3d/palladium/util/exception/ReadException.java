package me.cubert3d.palladium.util.exception;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        description = "Base exception that is thrown if an error occurs while reading the config.",
        authors = "cubert3d",
        date = "7/5/2021",
        type = ClassType.EXCEPTION
)

public abstract class ReadException extends RuntimeException {

    public ReadException() {

    }

    public ReadException(String errorMessage) {
        super(errorMessage);
    }
}
