package me.cubert3d.palladium.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@ClassInfo(
        description = "Contains information about a class.",
        authors = {
                "cubert3d"
        },
        date = "3/11/2021",
        type = ClassType.ANNOTATION
)

@Target(ElementType.TYPE)
public @interface ClassInfo {
    String description() default "No description.";
    String[] authors();                 // List of authors
    String date();                      // MM/DD/YYYY (i am murican)
    ClassType type();
    boolean complete() default true;
}
