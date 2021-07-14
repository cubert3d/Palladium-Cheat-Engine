package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.gui.text.provider.EnabledModulesProvider;
import me.cubert3d.palladium.gui.text.provider.TextProvider;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/8/2021",
        status = "in-progress"
)

public final class EnabledModListModule extends ToggleModule {

    public static final TextProvider modList = new EnabledModulesProvider();

    public EnabledModListModule() {
        super("ModList", "Displays a list of enabled modules on your screen.");
    }

    @Override
    protected void onEnable() {
        //TextHudRenderer.getTextManager().setTopRightList(modList);
    }

    @Override
    protected void onDisable() {
        //TextHudRenderer.getTextManager().clearTopRightList();
    }
}
