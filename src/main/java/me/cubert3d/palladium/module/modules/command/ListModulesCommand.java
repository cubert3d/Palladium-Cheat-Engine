package me.cubert3d.palladium.module.modules.command;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.module.AbstractModule;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleList;
import me.cubert3d.palladium.module.ModuleType;

// Written by cubert3d on 3/8/2021

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
