package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "4/10/2021",
        type = ClassType.MODULE
)

public final class ClickGUIModule extends ToggleModule {

    public ClickGUIModule() {
        super("ClickGUI", "Manages the Click GUI.");
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
