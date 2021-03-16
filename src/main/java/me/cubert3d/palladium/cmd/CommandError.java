package me.cubert3d.palladium.cmd;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import org.jetbrains.annotations.NotNull;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/4/2021",
        status = "complete"
)

public enum CommandError {
    COMMAND_NOT_FOUND("Command not found!"),
    MODULE_NOT_FOUND("Module not found!"),
    SETTING_NOT_FOUND("Setting not found!"),
    TOO_FEW_ARGUMENTS("Too few arguments!"),
    TOO_MANY_ARGUMENTS("Too many arguments!"),
    INVALID_ARGUMENTS("Invalid arguments!"),
    OUT_OF_BOUND_ARGUMENTS("Value out of bounds!");

    private final String errorMessage;

    CommandError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public final String getErrorMessage() {
        return errorMessage;
    }

    // Send an error message to the player's chat-HUD.
    public static void sendErrorMessage(@NotNull CommandError error) {
        Common.sendMessage(error.getErrorMessage());
    }
}
