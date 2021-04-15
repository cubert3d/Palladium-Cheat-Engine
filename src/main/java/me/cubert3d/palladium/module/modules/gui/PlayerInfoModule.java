package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.mixin.MinecraftClientAccessor;
import me.cubert3d.palladium.gui.TextHudRenderer;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Supplier;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/10/2021",
        status = "in-progress"
)

public final class PlayerInfoModule extends Module {

    private static final Supplier<ArrayList<String>> infoSupplier;

    public PlayerInfoModule() {
        super("Info", "Displays information about the player and the game on-screen.",
                ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
    }

    @Override
    protected void onEnable() {
        TextHudRenderer.getTextManager().setTopLeftSupplier(infoSupplier);
    }

    @Override
    protected void onDisable() {
        TextHudRenderer.getTextManager().clearTopLeftSupplier();
    }

    private static @NotNull String getPlayerName() {
        return "Name: " + Common.getClientPlayer().getName().getString();
    }

    private static @NotNull String getScore() {
        return "Score: " + Common.getClientPlayer().getScore();
    }

    private static String getCoordinatesString() {
        double x = Common.getClientPlayer().getX();
        double y = Common.getClientPlayer().getY();
        double z = Common.getClientPlayer().getZ();
        return String.format("Position: %.1f, %.1f, %.1f", x, y, z);
    }

    private static @NotNull String getDirectionFacing() {
        Direction direction = Common.getClientPlayer().getHorizontalFacing();
        String axis;
        switch (direction) {
            case NORTH:
                axis = "-Z";
                break;
            case SOUTH:
                axis = "+Z";
                break;
            case WEST:
                axis = "-X";
                break;
            case EAST:
                axis = "+X";
                break;
            default:
                return "Facing: " + direction;
        }
        return "Facing: " + direction + " (" + axis + ")";
    }

    @Contract(pure = true)
    private static @NotNull String getFPS() {
        int fps = MinecraftClientAccessor.getCurrentFPS();
        return "FPS: " + fps;
    }

    static {
        infoSupplier = () -> {
            ArrayList<String> strings = new ArrayList<>();

            strings.add(Palladium.NAME + " v" + Palladium.VERSION);
            strings.add(getPlayerName());
            strings.add(getScore());
            strings.add(getCoordinatesString());
            strings.add(getDirectionFacing());
            strings.add(getFPS());

            return strings;
        };
    }
}
