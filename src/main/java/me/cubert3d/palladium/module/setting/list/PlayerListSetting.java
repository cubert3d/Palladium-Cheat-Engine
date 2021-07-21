package me.cubert3d.palladium.module.setting.list;

import me.cubert3d.palladium.module.setting.SettingType;
import me.cubert3d.palladium.util.PlayerEntry;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

import java.util.Optional;
import java.util.UUID;

@ClassInfo(
        authors = "cubert3d",
        date = "7/21/2021",
        type = ClassType.SETTING
)

public final class PlayerListSetting extends ListSetting<PlayerEntry> {

    public PlayerListSetting(String name) {
        super(name);
    }

    @Override
    public SettingType getType() {
        return SettingType.LIST_PLAYER;
    }

    @Override
    protected String getElementAsString(PlayerEntry element) {
        Optional<UUID> uuid = element.getUuid();
        if (uuid.isPresent()) {
            return element.getLastKnownName() + ":" + uuid.get().toString();
        }
        else {
            return element.getLastKnownName();
        }
    }

    @Override
    public Optional<PlayerEntry> convertStringToElement(String string) {
        String[] split = string.split(":");
        if (split.length == 1) {
            String name = split[0];
            PlayerEntry entry = PlayerEntry.createEntry(name);
            return Optional.of(entry);
        }
        else if (split.length == 2) {
            String name = split[0];
            UUID uuid = UUID.fromString(split[1]);
            PlayerEntry entry = PlayerEntry.recreateEntry(name, uuid);
            return Optional.of(entry);
        }
        else {
            return Optional.empty();
        }
    }
}
