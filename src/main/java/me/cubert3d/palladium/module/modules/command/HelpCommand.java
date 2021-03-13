package me.cubert3d.palladium.module.modules.command;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.Main;
import me.cubert3d.palladium.cmd.CommandError;
import me.cubert3d.palladium.module.AbstractModule;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleList;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/12/2021",
        status = "in-progress"
)

public final class HelpCommand extends AbstractCommand {

    private static final int PAGE_SIZE = 10;

    public HelpCommand() {
        super("Help", "Lists the modules and commands, and their descriptions.", ModuleDevStatus.DEBUG_ONLY);
    }

    @Override
    public void execute(String[] args) {

        int pageNumber = 1;

        // Parse for the page number
        if (args.length == 1) {
            try {
                pageNumber = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                CommandError.sendErrorMessage(CommandError.INVALID_ARGUMENTS);
            }
        }
        else if (args.length > 1) {
            CommandError.sendErrorMessage(CommandError.TOO_MANY_ARGUMENTS);
            return;
        }

        Collection<AbstractModule> page = getPage(pageNumber);

        if (page != null && page.size() > 0) {
            Common.sendMessage(String.format("Displaying page %d of modules: ", pageNumber));
            for (AbstractModule module : page) {

                String message;

                if (Main.isDebugModeEnabled())
                    message = String.format("%s: %s (%s)", module.getName(), module.getDescription(), module.getDevStatus().toString());
                else
                    message = String.format("%s: %s", module.getName(), module.getDescription());

                Common.sendMessage(message);
            }
        }
        else {
            Common.sendMessage("No modules found.");
        }
    }

    @Contract(pure = true)
    private static @Nullable LinkedHashSet<AbstractModule> getPage(final int pageNumber) {

        // Cannot access a zero or negative page.
        if (pageNumber < 1) return null;

        LinkedHashSet<AbstractModule> page = new LinkedHashSet<>();

        // Used to count the index of the modules.
        int counter = 0;

        // The first and last index of the page.
        // Calculated once instead of every iteration.
        int firstIndex = (pageNumber - 1) * PAGE_SIZE;
        int lastIndex = pageNumber * PAGE_SIZE;

        // Iterate through the full set of modules to build the page.
        for (AbstractModule module : ModuleList.getModuleCollection()) {

            // Add the module if it is in the page range.
            if (counter >= firstIndex && counter < lastIndex)
                page.add(module);

            // If the page reaches the maximum page size, then break out of the loop.
            if (page.size() >= PAGE_SIZE)
                break;

            // Increment the index-counter.
            counter++;
        }

        return page;
    }
}
