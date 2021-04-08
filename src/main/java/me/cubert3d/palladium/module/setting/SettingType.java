package me.cubert3d.palladium.module.setting;

import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/7/2021"
)

public enum SettingType {
    BOOLEAN,
    INTEGER,
    DOUBLE,
    STRING,
    LIST_STRING,
    LIST_BLOCK,
    LIST_ENTITY,
    LIST_ITEM,
    LIST_PLAYER
}
