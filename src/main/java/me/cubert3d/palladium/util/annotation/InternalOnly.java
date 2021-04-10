package me.cubert3d.palladium.util.annotation;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/9/2021"
)

// Used for methods which might return null or even throw an exception,
// but is only to be used in particular places inside the code where
// the input is pre-determined (i.e. not user input).

public @interface InternalOnly {
}
