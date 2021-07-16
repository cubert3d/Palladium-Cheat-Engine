package me.cubert3d.palladium.module.modules.command;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.input.CommandError;
import me.cubert3d.palladium.module.modules.CommandModule;
import me.cubert3d.palladium.module.modules.Module;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedHashSet;

@ClassInfo(
        authors = "cubert3d",
        date = "3/12/2021",
        type = ClassType.MODULE
)

public final class HelpCommand extends CommandModule {

    private static final int PAGE_SIZE = 10;

    public HelpCommand() {
        super("Help", "Lists the modules and commands, and their descriptions.");
    }

    @Override
    protected void execute(String[] args) {
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

        Collection<Module> page = getPage(pageNumber);

        if (page != null && page.size() > 0) {
            printToChatHud(String.format("§6Displaying page §e%d§6 of modules: ", pageNumber));
            for (Module module : page) {
                printToChatHud(module.getName() + ": " + module.getDescription());
            }
        }
        else {
            printToChatHud("No modules found");
        }
    }

    @Contract(pure = true)
    private @Nullable LinkedHashSet<Module> getPage(final int pageNumber) {

        // Cannot access a zero or negative page.
        if (pageNumber < 1) return null;

        LinkedHashSet<Module> page = new LinkedHashSet<>();

        // Used to count the index of the modules.
        int counter = 0;

        // The first and last index of the page.
        // Calculated once instead of every iteration.
        int firstIndex = (pageNumber - 1) * PAGE_SIZE;
        int lastIndex = pageNumber * PAGE_SIZE;

        // Iterate through the full set of modules to build the page.
        for (Module module : Palladium.getInstance().getModuleManager().getModules()) {

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
