package me.cubert3d.palladium.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@ClassInfo(
        description = "Contains information about a callback.",
        authors = {
                "cubert3d"
        },
        date = "7/14/2021",
        type = ClassType.ANNOTATION
)

@Target(ElementType.TYPE)
public @interface CallbackInfo {
    Listener[] listeners();
    Interaction[] interactions();
}
