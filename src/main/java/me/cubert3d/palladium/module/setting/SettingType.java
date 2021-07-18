package me.cubert3d.palladium.module.setting;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        description = "Determines what data type a setting contains.",
        authors = "cubert3d",
        date = "4/7/2021",
        type = ClassType.MISC
)

public enum SettingType {
    BOOLEAN,
    INTEGER,
    DOUBLE,
    STRING,
    ENUM,
    KEY_BINDING,
    LIST_STRING,
    LIST_BLOCK,
    LIST_ENTITY,
    LIST_ITEM,
    LIST_PLAYER
}
