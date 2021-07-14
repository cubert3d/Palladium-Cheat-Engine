package me.cubert3d.palladium.module.modules.command;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.module.modules.CommandModule;
import me.cubert3d.palladium.module.modules.Module;
import me.cubert3d.palladium.util.Common;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "3/8/2021",
        type = ClassType.MODULE
)

public final class SearchCommand extends CommandModule {

    public SearchCommand() {
        super("Search", "Searches for modules and commands by name.");
    }

    @Override
    protected void execute(String[] args) {
        String searchPhrase = "";
        boolean search = false;

        if (args.length > 0 && args[0].length() > 0) {
            searchPhrase = args[0];
            search = true;
            Common.sendMessage(String.format("§6Showing modules beginning with \"§e%s§6\":", searchPhrase));
        }
        else {
            Common.sendMessage("§eShowing all modules:");
        }

        for (Module module : Palladium.getInstance().getModuleManager().getModules()) {
            if (!Palladium.getInstance().isDebugModeEnabled() && module.isAvailable()) {
                String name = module.getName();
                String description = module.getDescription();

                // Search function: if the name of the module starts with the first argument, then list it.
                if (!search || name.toLowerCase().startsWith(searchPhrase.toLowerCase())) {
                    Common.sendMessage(name + ": " + description);
                }
            }
            else if (Palladium.getInstance().isDebugModeEnabled()) {
                String name = module.getName();
                String description = module.getDescription();

                // Search function: if the name of the module starts with the first argument, then list it.
                if (!search || name.toLowerCase().startsWith(searchPhrase.toLowerCase())) {
                    Common.sendMessage(name + ": " + description);
                }
            }
        }
    }
}
