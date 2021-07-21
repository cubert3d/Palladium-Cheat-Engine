package me.cubert3d.palladium.module.setting.single;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "7/21/2021",
        type = ClassType.MISC
)

public interface EnumDescribed {
    String getDescription();
}
