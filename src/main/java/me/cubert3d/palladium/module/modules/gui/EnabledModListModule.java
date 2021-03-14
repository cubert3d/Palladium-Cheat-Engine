package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import me.cubert3d.palladium.util.annotation.DebugOnly;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/8/2021",
        status = "benched"
)

public final class EnabledModListModule extends Module {

    public EnabledModListModule() {
        super("ModList", "Displays a list of enabled modules on your screen.",
                ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }

    @Override
    protected void onLoad() {


    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    @DebugOnly
    private void test() {


    }
}
