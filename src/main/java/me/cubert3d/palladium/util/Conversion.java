package me.cubert3d.palladium.util;

import me.cubert3d.palladium.util.annotation.ClassDescription;

import java.util.Optional;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/13/2021",
        status = "in-progress"
)

public final class Conversion {

    private Conversion() {}

    public static Optional<Boolean> parseBoolean(String string) {
        Optional<Boolean> optional = Optional.empty();
        string = string.trim().toLowerCase();

        if (string.equals("true"))
            optional = Optional.of(true);
        else if (string.equals("false"))
            optional = Optional.of(false);

        return optional;
    }

    public static Optional<Integer> parseInteger(String string) {
        Optional<Integer> optional = Optional.empty();

        try {
            optional = Optional.of(Integer.valueOf(string));
        }
        catch (NumberFormatException ignored) {

        }
        return optional;
    }

    public static Optional<Double> parseDouble(String string) {
        Optional<Double> optional = Optional.empty();

        try {
            optional = Optional.of(Double.valueOf(string));
        }
        catch (NumberFormatException ignored) {

        }
        return optional;
    }

    // All integers are rational numbers, but not all rational numbers are integers

}
