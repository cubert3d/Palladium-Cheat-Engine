package me.cubert3d.palladium.util.annotation;

@ClassInfo(
        description = "Contains information about a callback listener.",
        authors = {
                "cubert3d"
        },
        date = "7/14/2021",
        type = ClassType.ANNOTATION
)

public @interface Listener {
    Class<?> where();                   // Where this listener is located.
    String[] method() default "onLoad";
}
