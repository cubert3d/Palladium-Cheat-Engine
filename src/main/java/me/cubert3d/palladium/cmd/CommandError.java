package me.cubert3d.palladium.cmd;

import me.cubert3d.palladium.Common;
import org.jetbrains.annotations.NotNull;

public enum CommandError {
    COMMAND_NOT_FOUND("Command not found!"),
    MODULE_NOT_FOUND("Module not found!"),
    TOO_FEW_ARGUMENTS("Too few arguments!"),
    TOO_MANY_ARGUMENTS("Too many arguments!"),
    INVALID_ARGUMENTS("Invalid arguments!");

    private final String errorMessage;

    CommandError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public final String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sends the error message to the player's chat-HUD.
     *
     * @param error  the particular command-error that had occurred
     */
    public static void sendErrorMessage(@NotNull CommandError error) {
        Common.sendMessage(error.getErrorMessage());
    }
}
