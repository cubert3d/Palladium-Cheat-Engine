package me.cubert3d.palladium.module.setting2;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

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

    @Contract(pure = true)
    public static @Nullable SettingType getTypeFromObject(Object object) {
        if (object instanceof Boolean)
            return BOOLEAN;
        else if (object instanceof Integer)
            return INTEGER;
        else if (object instanceof Double)
            return DOUBLE;
        else if (object instanceof String)
            return STRING;

        return null;
    }
}
