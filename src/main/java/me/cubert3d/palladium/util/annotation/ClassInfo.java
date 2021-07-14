package me.cubert3d.palladium.util.annotation;

@ClassInfo(
        authors = {
                "cubert3d"
        },
        date = "3/11/2021",
        type = ClassType.ANNOTATION
)

public @interface ClassInfo {
    String[] authors();                 // List of authors
    String date();                      // MM/DD/YYYY (i am murican)
    ClassType type();
}
