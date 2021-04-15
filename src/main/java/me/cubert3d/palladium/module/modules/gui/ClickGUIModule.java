package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.gui.ClickGUI;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/10/2021",
        status = "in-progress"
)

public final class ClickGUIModule extends Module {

    public ClickGUIModule() {
        super("ClickGUI", "Manages the Click GUI.", ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }

    @Override
    protected void onEnable() {
        ClickGUI.openClickGUI();
    }

    @Override
    protected void onDisable() {
        ClickGUI.closeClickGUI();
    }
}
