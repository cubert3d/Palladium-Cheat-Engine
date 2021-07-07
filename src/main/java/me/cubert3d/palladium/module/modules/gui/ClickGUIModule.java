package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/10/2021",
        status = "in-progress"
)

public final class ClickGUIModule extends ToggleModule {

    public ClickGUIModule() {
        super("ClickGUI", "Manages the Click GUI.", ModuleDevStatus.DEBUG_ONLY);
    }

    @Override
    protected void onEnable() {
        Palladium.getInstance().getGuiRenderer().getClickGUI().open();
    }

    @Override
    protected void onDisable() {
        Palladium.getInstance().getGuiRenderer().getClickGUI().close();
    }
}
