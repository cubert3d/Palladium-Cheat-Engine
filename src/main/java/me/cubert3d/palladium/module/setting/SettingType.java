package me.cubert3d.palladium.module.setting;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/5/2021",
        status = "complete"
)

public enum SettingType {
    BOOLEAN(false),
    INTEGER(true),
    DOUBLE(true),
    STRING(false),
    COMMAND(false); // To be implemented.

    private final boolean numberType;

    SettingType(boolean numberType) {
        this.numberType = numberType;
    }

    public final boolean isNumberType() {
        return numberType;
    }

    @Contract(pure = true)
    public static Optional<SettingType> getTypeFromObject(Object object) {
        if (object instanceof Boolean)
            return Optional.of(BOOLEAN);
        else if (object instanceof Integer)
            return Optional.of(INTEGER);
        else if (object instanceof Double)
            return Optional.of(DOUBLE);
        else if (object instanceof String)
            return Optional.of(STRING);
        return Optional.empty();
    }
}
