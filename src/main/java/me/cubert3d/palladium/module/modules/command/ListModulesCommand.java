package me.cubert3d.palladium.module.modules.command;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.module.AbstractModule;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleList;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.ClassDescription;

// Written by cubert3d on 3/8/2021

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/8/2021",
        status = "in-progress"
)

public final class ListModulesCommand extends AbstractModule {

    public ListModulesCommand() {
        super("ListModules", "Lists the modules available in Palladium Cheat Engine.",
                ModuleType.EXECUTE_ONCE, ModuleDevStatus.AVAILABLE);
    }

    @Override
    public void execute(String[] args) {
        Common.sendMessage("Modules in Palladium Cheat Engine: ");
        for (AbstractModule module : ModuleList.getModuleCollection()) {
            if (module.isAvailable()) {
                String name = module.getName();
                String description = module.getDescription();

                // Search function: if the name of the module starts with the first argument, then list it
                if (args.length > 0) {
                    String search = args[0];
                    if (name.toLowerCase().startsWith(search.toLowerCase()))
                        Common.sendMessage(name + ": " + description);
                }
                else
                    Common.sendMessage(name + ": " + description);

            }
        }
    }
}
