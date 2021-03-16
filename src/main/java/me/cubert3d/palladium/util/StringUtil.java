package me.cubert3d.palladium.util;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import org.jetbrains.annotations.NotNull;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/14/2021",
        status = "in-progress"
)

public final class StringUtil {

    private StringUtil() {}

    public static @NotNull String capitalizeFirst(@NotNull String string) {
        if (string.length() > 0) {
            string = string.toLowerCase();
            char[] chars = string.toCharArray();
            chars[0] = charToUpperCase(chars[0]);
            return String.valueOf(chars);
        }
        else
            return string;
    }

    public static char charToUpperCase(char c) {
        switch (c) {
            case 'a': return 'A';
            case 'b': return 'B';
            case 'c': return 'C';
            case 'd': return 'D';
            case 'e': return 'E';
            case 'f': return 'F';
            case 'g': return 'G';
            case 'h': return 'H';
            case 'i': return 'I';
            case 'j': return 'J';
            case 'k': return 'K';
            case 'l': return 'L';
            case 'm': return 'M';
            case 'n': return 'N';
            case 'o': return 'O';
            case 'p': return 'P';
            case 'q': return 'Q';
            case 'r': return 'R';
            case 's': return 'S';
            case 't': return 'T';
            case 'u': return 'U';
            case 'v': return 'V';
            case 'w': return 'W';
            case 'x': return 'X';
            case 'y': return 'Y';
            case 'z': return 'Z';
            default: return c;
        }
    }
}
