package me.cubert3d.palladium.input;

import me.cubert3d.palladium.util.Common;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import org.jetbrains.annotations.NotNull;

@ClassInfo(
        authors = "cubert3d",
        date = "3/4/2021",
        type = ClassType.MISC
)

public enum CommandError {
    MODULE_NOT_FOUND("Module not found!"),
    SETTING_NOT_FOUND("Setting not found!"),
    TOO_FEW_ARGUMENTS("Too few arguments!"),
    TOO_MANY_ARGUMENTS("Too many arguments!"),
    INVALID_ARGUMENTS("Invalid arguments!"),
    NUMBER_OUT_OF_BOUNDS("Number out of bounds!");

    private final String errorMessage;

    CommandError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public final String getErrorMessage() {
        return errorMessage;
    }

    // Send an error message to the player's chat-HUD.
    public static void sendErrorMessage(@NotNull CommandError error) {
        Common.sendMessage("§c" + error.getErrorMessage());
    }
}
