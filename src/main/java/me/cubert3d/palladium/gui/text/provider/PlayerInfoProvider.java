package me.cubert3d.palladium.gui.text.provider;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.mixin.accessors.MinecraftClientAccessor;
import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.util.Common;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class PlayerInfoProvider extends TextProvider {

    public PlayerInfoProvider() {

    }

    @Override
    public @NotNull ColorText getHeader() {
        return getClientInfo();
    }

    @Override
    public @NotNull ArrayList<ColorText> getBody() {
        ArrayList<ColorText> text = new ArrayList<>();

        text.add(getPlayerName());
        text.add(getScore());
        text.add(getCoordinatesString());
        text.add(getDirectionFacing());
        text.add(getFPS());

        return text;
    }

    private @NotNull ColorText getClientInfo() {
        return new ColorText(Palladium.NAME + " v" + Palladium.VERSION);
    }

    private @NotNull ColorText getPlayerName() {
        return new ColorText("Name: " + MinecraftClient.getInstance().player.getName().getString());
    }

    private @NotNull ColorText getScore() {
        return new ColorText("Score: " + Common.getPlayer().getScore());
    }

    private @NotNull ColorText getCoordinatesString() {
        double x = Common.getPlayer().getX();
        double y = Common.getPlayer().getY();
        double z = Common.getPlayer().getZ();
        return new ColorText(String.format("Position: %.1f, %.1f, %.1f", x, y, z));
    }

    private @NotNull ColorText getDirectionFacing() {
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

    private @NotNull ColorText getFPS() {
        int fps = MinecraftClientAccessor.getCurrentFPS();
        return new ColorText("FPS: " + fps);
    }
}
