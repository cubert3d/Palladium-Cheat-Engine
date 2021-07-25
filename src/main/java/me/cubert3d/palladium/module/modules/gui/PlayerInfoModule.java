package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.mixin.accessors.MinecraftClientAccessor;
import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.gui.text.TextProvider;
import me.cubert3d.palladium.gui.window.TextProviderWindow;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@ClassInfo(
        authors = "cubert3d",
        date = "4/10/2021",
        type = ClassType.MODULE
)

public final class PlayerInfoModule extends AbstractHudModule {

    private final PlayerInfoProvider infoList;

    public PlayerInfoModule() {
        super("Info", "Displays information about the player and the game on-screen.");
        this.infoList = new PlayerInfoProvider();
    }

    @Override
    protected final void onEnable() {
        super.onEnable();
        getTextManager().setTopLeftList(infoList);
    }

    @Override
    protected final void onDisable() {
        super.onDisable();
        getTextManager().clearTopLeftList();
    }

    @Override
    protected final @NotNull TextProviderWindow createWindow() {
        TextProviderWindow newWindow = TextProviderWindow.newDisplayWindow("player_info", infoList, this);
        newWindow.setX(25);
        newWindow.setY(25);
        newWindow.setWidth(150);
        newWindow.setHeight(91);
        newWindow.setColor(Colors.BACKGROUND_LAVENDER);
        return newWindow;
    }

    private static class PlayerInfoProvider extends TextProvider {

        public PlayerInfoProvider() {
            super();
        }

        @Override
        public ColorText getTitle() {
            return getClientInfo();
        }

        @Override
        public ArrayList<ColorText> getBody() {
            ArrayList<ColorText> text = new ArrayList<>();
            ClientPlayerEntity player = MinecraftClient.getInstance().player;

            if (player != null) {
                text.add(getPlayerName(player));
                text.add(getScore(player));
                text.add(getCoordinatesString(player));
                text.add(getDirectionFacing(player));
                text.add(getFPS());
            }

            return text;
        }

        private @NotNull ColorText getClientInfo() {
            return new ColorText(Palladium.NAME + " v" + Palladium.VERSION);
        }

        private @NotNull ColorText getPlayerName(@NotNull ClientPlayerEntity player) {
            return new ColorText("Name: " + player.getName().getString());
        }

        private @NotNull ColorText getScore(@NotNull ClientPlayerEntity player) {
            return new ColorText("Score: " + player.getScore());
        }

        private @NotNull ColorText getCoordinatesString(@NotNull ClientPlayerEntity player) {
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            return new ColorText(String.format("Position: %.1f, %.1f, %.1f", x, y, z));
        }

        private @NotNull ColorText getDirectionFacing(@NotNull ClientPlayerEntity player) {
            Direction direction = player.getHorizontalFacing();
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
}
