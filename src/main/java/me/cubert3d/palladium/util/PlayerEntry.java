package me.cubert3d.palladium.util;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

@ClassInfo(
        authors = "cubert3d",
        date = "7/21/2021",
        type = ClassType.MISC
)

public final class PlayerEntry {

    private String lastKnownName;
    private UUID uuid;

    private PlayerEntry(String name, UUID uuid) {
        this.lastKnownName = name;
        this.uuid = uuid;
    }

    private PlayerEntry(String name) {
        this.lastKnownName = name;
        this.uuid = null;
    }

    public final String getLastKnownName() {
        return lastKnownName;
    }

    public final Optional<UUID> getUuid() {
        return Optional.ofNullable(uuid);
    }

    public final void updateName(String name) {
        this.lastKnownName = name;
    }

    public final void setUUID(UUID uuid) {
        if (this.uuid == null) {
            this.uuid = uuid;
        }
    }

    public Optional<PlayerListEntry> getPlayer() {
        ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();
        if (networkHandler != null) {
            PlayerListEntry playerListEntry;
            if (uuid != null) {
                playerListEntry = networkHandler.getPlayerListEntry(uuid);
                if (playerListEntry != null) {
                    updateName(playerListEntry.getProfile().getName());
                }
            }
            else {
                playerListEntry = networkHandler.getPlayerListEntry(lastKnownName);
                if (playerListEntry != null) {
                    setUUID(playerListEntry.getProfile().getId());
                }
            }
            return Optional.ofNullable(playerListEntry);
        }
        return Optional.empty();
    }

    public static @NotNull PlayerEntry createEntry(@NotNull String name) {
        PlayerEntry entry = new PlayerEntry(name.trim());
        entry.getPlayer();
        return entry;
    }

    public static PlayerEntry recreateEntry(String name, UUID uuid) {
        return new PlayerEntry(name, uuid);
    }
}
