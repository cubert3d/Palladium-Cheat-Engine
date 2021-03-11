package me.cubert3d.palladium.util.annotation;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/11/2021",
        status = "in-progress"
)

public @interface ClassDescription {
    String[] authors();                 // List of authors
    String date();                      // MM/DD/YYYY (i am murican)
    String status() default "complete"; // Status of the development of this class
        // "complete": class is in some state of completion (still subject to future revision)
        // "in-progress": class is still being developed
        // "benched": class has been put aside to be worked on later
        // "unused": class is unused
}
