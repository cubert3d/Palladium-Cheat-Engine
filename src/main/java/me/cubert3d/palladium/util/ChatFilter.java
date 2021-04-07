package me.cubert3d.palladium.util;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import me.cubert3d.palladium.util.annotation.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/12/2021",
        status = "complete"
)

@UtilityClass
public final class ChatFilter {

    private ChatFilter() {}

    private static final ArrayList<String> phrases = new ArrayList<>();

    public static ArrayList<String> getPhrases() {
        return phrases;
    }

    public static boolean contains(@NotNull String phrase) {
        phrase = phrase.trim().toLowerCase();
        return phrases.contains(phrase);
    }

    public static boolean shouldMsgBeFiltered(String message) {
        message = message.trim().toLowerCase();
        for (String phrase : phrases) {
            if (message.contains(phrase.trim().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    // Adds a phrase to the list and return the new size of the list.
    public static void addPhrase(final @NotNull String phrase) {
        phrases.add(phrase.trim().toLowerCase());
    }

    // Attempt to remove the given phrase, and return whether or not such a phrase was found.
    public static boolean removePhrase(String phrase) {
        phrase = phrase.trim().toLowerCase();
        return phrases.remove(phrase);
    }
}
