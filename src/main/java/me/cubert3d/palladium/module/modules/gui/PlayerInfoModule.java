package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.gui.text.provider.PlayerInfoProvider;
import me.cubert3d.palladium.gui.text.provider.TextProvider;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "4/10/2021",
        type = ClassType.MODULE
)

public final class PlayerInfoModule extends ToggleModule {

    private static final TextProvider infoList = new PlayerInfoProvider();

    public PlayerInfoModule() {
        super("Info", "Displays information about the player and the game on-screen.");
    }

    @Override
    protected void onEnable() {
        Palladium.getInstance().getGuiRenderer().getTextHudRenderer().getTextManager().setTopLeftList(infoList);
    }

    @Override
    protected void onDisable() {
        Palladium.getInstance().getGuiRenderer().getTextHudRenderer().getTextManager().clearTopLeftList();
    }
}
