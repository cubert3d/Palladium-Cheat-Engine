package me.cubert3d.palladium.util.exception;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        description = "Thrown when a string is passed to a setting's setFromString function and the string cannot be parsed.",
        authors = "cubert3d",
        date = "7/5/2021",
        type = ClassType.EXCEPTION
)

public final class SettingParseException extends ReadException {

    public SettingParseException() {

    }

    public SettingParseException(String errorMessage) {
        super(errorMessage);
    }
}
