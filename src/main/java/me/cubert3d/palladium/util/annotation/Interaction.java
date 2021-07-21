package me.cubert3d.palladium.util.annotation;

@ClassInfo(
        description = "Contains information about a callback interaction, usually inside mixins.",
        authors = {
                "cubert3d"
        },
        date = "7/14/2021",
        type = ClassType.ANNOTATION
)

public @interface Interaction {
    Class<?> where();
    String[] method();
}
