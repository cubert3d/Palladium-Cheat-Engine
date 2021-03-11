package me.cubert3d.palladium.module.setting;

import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/5/2021",
        status = "unused"
)

public enum SettingType {
    BOOLEAN(false),
    INTEGER(true),
    DOUBLE(true),
    STRING(false);

    private final boolean numberType;

    SettingType(boolean numberType) {
        this.numberType = numberType;
    }

    public final boolean isNumberType() {
        return numberType;
    }
}
