package me.cubert3d.palladium.module.modules.command;

import me.cubert3d.palladium.input.CommandError;
import me.cubert3d.palladium.input.CommandListener;
import me.cubert3d.palladium.module.modules.CommandModule;
import org.jetbrains.annotations.NotNull;

public final class ExecuteCommand extends CommandModule {

    public ExecuteCommand() {
        super("Execute", "Executes a Palladium command.");
    }

    @Override
    protected final void execute(String @NotNull [] args) {
        if (args.length > 0) {
            CommandListener.processCommand(args);
        }
        else {
            CommandError.sendErrorMessage(CommandError.TOO_FEW_ARGUMENTS);
        }
    }
}
