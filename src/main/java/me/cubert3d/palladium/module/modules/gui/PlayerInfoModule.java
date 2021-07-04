package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.gui.TextHudRenderer;
import me.cubert3d.palladium.gui.text.provider.PlayerInfoProvider;
import me.cubert3d.palladium.gui.text.provider.TextProvider;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/10/2021",
        status = "in-progress"
)

public final class PlayerInfoModule extends ToggleModule {

    private static final TextProvider infoList = new PlayerInfoProvider();

    public PlayerInfoModule() {
        super("Info", "Displays information about the player and the game on-screen.",
                ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
    }

    @Override
    protected void onEnable() {
        TextHudRenderer.getTextManager().setTopLeftList(infoList);
    }

    @Override
    protected void onDisable() {
        TextHudRenderer.getTextManager().clearTopLeftList();
    }
}
