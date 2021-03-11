package me.cubert3d.palladium;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/*
    A collection of common, useful fields and methods, to be used anywhere
 */

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/4/2021",
        status = "in-progress"
)

public final class Common {

    // COMMON GETTERS

    public static MinecraftClient getMC() {
        return MinecraftClient.getInstance();
    }

    public static ClientPlayerEntity getClientPlayer() {
        return getMC().player;
    }

    public static ClientWorld getClientWorld() {
        return getMC().world;
    }

    public static boolean isWorldClient() {
        return getClientPlayer().world.isClient();
    }

    public static @Nullable GameMode getGameMode() {

        PlayerListEntry playerListEntry = Objects.requireNonNull(getMC().getNetworkHandler()).getPlayerListEntry(getClientPlayer().getUuid());

        if (playerListEntry != null)
            return playerListEntry.getGameMode();
        else
            return null;
    }

    // COMMON METHODS

    public static void sendMessage(Text message) {
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(message);
    }

    public static void sendMessage(String message) {
        Text textMessage = new LiteralText(message);
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
    }
}
