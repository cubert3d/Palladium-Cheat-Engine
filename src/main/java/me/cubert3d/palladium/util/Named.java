package me.cubert3d.palladium.util;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "3/3/2021",
        type = ClassType.MISC
)

public interface Named {
    String getName();
}
