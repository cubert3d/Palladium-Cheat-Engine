package me.cubert3d.palladium.module.modules.player;

import me.cubert3d.palladium.event.callback.ChatFilterCallback;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.module.setting.list.StringListSetting;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

import java.util.List;

@ClassInfo(
        authors = "cubert3d",
        date = "3/12/2021",
        type = ClassType.MODULE
)

public final class ChatFilterModule extends ToggleModule {

    private final StringListSetting blacklistSetting;

    public ChatFilterModule() {
        super("ChatFilter", "Blocks any messages that contain a blacklisted phrase.");
        this.blacklistSetting = new StringListSetting("Blacklist", "The phrases to be filtered.");
        this.addSetting(blacklistSetting);
    }

    @Override
    public void onLoad() {
        ChatFilterCallback.EVENT.register(message -> {
            if (this.isEnabled()) {
                return shouldFilterMessage(message);
            }
            else {
                return false;
            }
        });
    }

    private boolean shouldFilterMessage(String message) {
        List<String> blacklist = blacklistSetting.getList();
        message = message.trim().toLowerCase();
        for (String phrase : blacklist) {
            if (message.contains(phrase.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
