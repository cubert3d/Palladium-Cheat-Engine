package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.mixin.MinecraftClientAccessor;
import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.gui.TextHudRenderer;
import me.cubert3d.palladium.gui.text.TextList;
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

    private static final TextList infoList;

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

    private static @NotNull ColorText getClientInfo() {
        return new ColorText(Palladium.NAME + " v" + Palladium.VERSION);
    }

    private static @NotNull ColorText getPlayerName() {
        return new ColorText("Name: " + Common.getPlayer().getName().getString());
    }

    private static @NotNull ColorText getScore() {
        return new ColorText("Score: " + Common.getPlayer().getScore());
    }

    private static @NotNull ColorText getCoordinatesString() {
        double x = Common.getPlayer().getX();
        double y = Common.getPlayer().getY();
        double z = Common.getPlayer().getZ();
        return new ColorText(String.format("Position: %.1f, %.1f, %.1f", x, y, z));
    }

    private static @NotNull ColorText getDirectionFacing() {
        Direction direction = Common.getPlayer().getHorizontalFacing();
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
                return new ColorText("Facing: " + direction);
        }
        return new ColorText("Facing: " + direction + " (" + axis + ")");
    }

    @Contract(pure = true)
    private static @NotNull ColorText getFPS() {
        int fps = MinecraftClientAccessor.getCurrentFPS();
        return new ColorText("FPS: " + fps);
    }

    static {
        infoList = new TextList(
                PlayerInfoModule::getClientInfo,
                () -> {
                    ArrayList<ColorText> text = new ArrayList<>();

                    text.add(getPlayerName());
                    text.add(getScore());
                    text.add(getCoordinatesString());
                    text.add(getDirectionFacing());
                    text.add(getFPS());

                    return text;
                }
        );
    }
}
