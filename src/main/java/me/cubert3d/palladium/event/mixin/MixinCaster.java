package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "7/5/2021",
        type = ClassType.MISC
)

public interface MixinCaster<T> {
    default T self() {
        return (T) this;
    }
}
