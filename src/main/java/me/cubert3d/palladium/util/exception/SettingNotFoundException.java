package me.cubert3d.palladium.util.exception;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        description = "Thrown when reading the config file and a setting cannot be identified.",
        authors = "cubert3d",
        date = "7/5/2021",
        type = ClassType.EXCEPTION
)

public final class SettingNotFoundException extends ReadException {

    public SettingNotFoundException() {

    }

    public SettingNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
