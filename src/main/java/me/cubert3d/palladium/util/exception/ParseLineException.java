package me.cubert3d.palladium.util.exception;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        description = "Thrown when reading the config file and a line cannot be parsed.",
        authors = "cubert3d",
        date = "7/14/2021",
        type = ClassType.EXCEPTION
)

public final class ParseLineException extends ReadException {

    public ParseLineException() {

    }

    public ParseLineException(String errorMessage) {
        super(errorMessage);
    }
}
