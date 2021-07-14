package me.cubert3d.palladium.util;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameMode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/*
A collection of common, useful fields and methods, to be used anywhere
 */

@ClassInfo(
        authors = "cubert3d",
        date = "3/4/2021",
        type = ClassType.UTILITY
)

public final class Common {

    private Common() {}

    // COMMON GETTERS

    public static MinecraftClient getMC() {
        return MinecraftClient.getInstance();
    }

    public static ClientPlayerEntity getPlayer() {
        return getMC().player;
    }



    // PLAYER

    public static @Nullable GameMode getGameMode() {

        PlayerListEntry playerListEntry = Objects.requireNonNull(getMC().getNetworkHandler()).getPlayerListEntry(getPlayer().getUuid());

        if (playerListEntry != null)
            return playerListEntry.getGameMode();
        else
            return null;
    }



    // COMMON METHODS

    public static void sendMessage(String message) {
        Text textMessage = new LiteralText(message);
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
    }



    // RENDERING

    public static void reloadRenderer() {
        getMC().worldRenderer.reload();
    }



    // IDENTIFIER AND REGISTRY

    public static final String DEFAULT_NAMESPACE = "minecraft";
    public static final char IDENTIFIER_DELIMITER = ':';

    /*
     Checks if a given string contains the Identifier delimiter, which
     a colon (":"). It skips the colon if it is at the beginning or end
     of the string, but then checks for a delimiter in the rest of the
     string. It returns the index of the delimiter--to make sure that
     the given string can be split along the proper point, and not at
     the beginning or end, in case there was a colon in either of those
     places--or it returns -1, if no delimiter was found.
    */
    public static int getDelimiterIndexInString(@NotNull String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == IDENTIFIER_DELIMITER
                    && i > 1 && i < s.length() - 1)
                return i;
        }
        return -1;
    }

    // Builds an Identifier from a given string. It checks if the
    // string has the namespace or not: if it does not, then it just
    // supplies the default namespace ("minecraft:").
    @Contract("_ -> new")
    public static @NotNull Identifier buildID(String string) {
        string = string.trim();
        String namespace, path;
        int delimiterIndex = getDelimiterIndexInString(string);
        if (delimiterIndex != -1) {
            namespace = string.split(":")[0];
            path = string.split(":")[1];
        }
        else {
            namespace = DEFAULT_NAMESPACE;
            path = string;
        }
        return new Identifier(namespace, path);
    }

    public static @NotNull Block getBlockFromString(String name) {
        return Registry.BLOCK.get(buildID(name));
    }

    public static @NotNull EntityType<? extends Entity> getEntityTypeFromString(String name) {
        return Registry.ENTITY_TYPE.get(buildID(name));
    }

    public static @NotNull Item getItemFromString(String name) {
        return Registry.ITEM.get(buildID(name));
    }
}
