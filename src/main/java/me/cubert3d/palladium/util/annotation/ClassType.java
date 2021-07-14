package me.cubert3d.palladium.util.annotation;

@ClassInfo(
        description = "The various types of classes in this project.",
        authors = "cubert3d",
        date = "7/13/2021",
        type = ClassType.MISC
)

public enum ClassType {
    MAIN,
    MANAGER,
    RENDERER,
    MODULE,
    SETTING,
    WIDGET,
    PROVIDER,
    MIXIN,
    CALLBACK,
    LISTENER,
    UTILITY,
    EXCEPTION,
    ANNOTATION,
    MISC
}
