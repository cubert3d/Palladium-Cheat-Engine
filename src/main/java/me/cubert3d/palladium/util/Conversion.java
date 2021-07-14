package me.cubert3d.palladium.util;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "3/13/2021",
        type = ClassType.UTILITY
)

public final class Conversion {

    private Conversion() {}

    public static Optional<Boolean> parseBoolean(String string) {
        string = string.trim();

        if (string.equalsIgnoreCase("true"))
            return Optional.of(true);
        else if (string.equalsIgnoreCase("false"))
            return Optional.of(false);

        return Optional.empty();
    }

    public static Optional<Integer> parseInteger(String string) {
        try {
            return Optional.of(Integer.valueOf(string));
        }
        catch (NumberFormatException ignored) {
            return Optional.empty();
        }
    }

    public static Optional<Double> parseDouble(String string) {
        try {
            return Optional.of(Double.valueOf(string));
        }
        catch (NumberFormatException ignored) {
            return Optional.empty();
        }
    }

    // All integers are rational numbers, but not all rational numbers are integers

}
