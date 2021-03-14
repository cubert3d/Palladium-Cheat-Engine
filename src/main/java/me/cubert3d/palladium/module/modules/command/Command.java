package me.cubert3d.palladium.module.modules.command;

import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.ArgumentTree;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/12/2021",
        status = "in-progress"
)

abstract class Command extends Module {

    protected ArgumentTree argumentTree;

    protected Command(String name, String description, ModuleDevStatus status) {
        super(name, description, ModuleType.EXECUTE_ONCE, status);
    }

    private static void parseArguments(String[] args) {

    }
}
