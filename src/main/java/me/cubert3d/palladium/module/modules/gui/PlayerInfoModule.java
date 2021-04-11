package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.gui.HudRenderer;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;

import java.util.ArrayList;
import java.util.function.Supplier;

public final class PlayerInfoModule extends Module {

    private static final Supplier<ArrayList<String>> infoSupplier;

    public PlayerInfoModule() {
        super("Info", "Displays information about the player and the game on-screen.",
                ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }

    @Override
    protected void onEnable() {
        HudRenderer.getTextManager().setTopLeftSupplier(infoSupplier);
    }

    @Override
    protected void onDisable() {
        HudRenderer.getTextManager().clearTopLeftSupplier();
    }

    private static String getPlayerName() {
        return "Name: " + Common.getClientPlayer().getName().getString();
    }

    private static String getCoordinatesString() {
        double x = Common.getClientPlayer().getX();
        double y = Common.getClientPlayer().getY();
        double z = Common.getClientPlayer().getZ();
        return String.format("Position: %.1f, %.1f, %.1f", x, y, z);
    }

    static {
        infoSupplier = () -> {
            ArrayList<String> strings = new ArrayList<>();

            strings.add(Palladium.NAME + " v" + Palladium.VERSION);
            strings.add(getPlayerName());
            strings.add(getCoordinatesString());

            return strings;
        };
    }
}
