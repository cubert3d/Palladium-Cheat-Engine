package me.cubert3d.palladium.module.modules.command;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.module.modules.CommandModule;
import me.cubert3d.palladium.util.Common;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import org.jetbrains.annotations.NotNull;

// Written by cubert3d on 3/8/2021

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/8/2021",
        status = "complete"
)

public final class SearchCommand extends CommandModule {

    public SearchCommand() {
        super("Search", "Searches for modules and commands by name.",
                ModuleType.EXECUTE_ONCE, ModuleDevStatus.AVAILABLE);
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

        for (Module module : ModuleManager.getModules()) {
            if (!Palladium.isDebugModeEnabled() && module.isAvailable()) {
                String name = module.getName();
                String description = module.getDescription();

                // Search function: if the name of the module starts with the first argument, then list it.
                if (!search || name.toLowerCase().startsWith(searchPhrase.toLowerCase())) {
                    Common.sendMessage(name + ": " + description);
                }
            }
            else if (Palladium.isDebugModeEnabled()) {
                String name = module.getName();
                String description = module.getDescription();

                // Search function: if the name of the module starts with the first argument, then list it.
                if (!search || name.toLowerCase().startsWith(searchPhrase.toLowerCase())) {
                    if (module.getDevStatus().equals(ModuleDevStatus.DEBUG_ONLY)) {
                        Common.sendMessage(name + ": " + description + " §c(" + module.getDevStatus() + ")");
                    }
                    else {
                        Common.sendMessage(name + ": " + description + " (" + module.getDevStatus() + ")");
                    }
                }
            }
        }
    }
}
