package me.cubert3d.palladium.module.modules.command;

import me.cubert3d.palladium.module.command.CommandError;
import me.cubert3d.palladium.module.command.CommandListener;
import me.cubert3d.palladium.module.modules.CommandModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import org.jetbrains.annotations.NotNull;

@ClassInfo(
        authors = "cubert3d",
        date = "7/15/2021",
        type = ClassType.MODULE
)

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
