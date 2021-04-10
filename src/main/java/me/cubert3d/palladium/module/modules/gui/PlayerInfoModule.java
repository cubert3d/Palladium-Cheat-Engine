package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.gui.HudRenderer;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;

import java.util.ArrayList;

public final class PlayerInfoModule extends Module {

    private final ArrayList<String> stringList = new ArrayList<>();

    public PlayerInfoModule() {
        super("Info", "Displays information about the player and the game on-screen.",
                ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
        this.stringList.add("Palladium Cheat Engine v" + Palladium.VERSION);
    }

    @Override
    protected void onEnable() {
        HudRenderer.getTextManager().getTopLeftStrings().addAll(stringList);
    }

    @Override
    protected void onDisable() {
        HudRenderer.getTextManager().getTopLeftStrings().removeAll(stringList);
    }
}
