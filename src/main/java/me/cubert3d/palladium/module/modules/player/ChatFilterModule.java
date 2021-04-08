package me.cubert3d.palladium.module.modules.player;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.module.setting.list.StringListSetting;
import me.cubert3d.palladium.util.ChatFilter;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/12/2021",
        status = "complete"
)


public final class ChatFilterModule extends Module {

    public ChatFilterModule() {
        super("ChatFilter", "Blocks any messages that contain a blocked phrase.",
                ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
        addSetting(new StringListSetting("Blacklist"));
    }

    private void listBlockedPhrases() {
        // TODO: allow for search function

        String joinedPhrases;
        int blockedPhrasesCount = ChatFilter.getPhrases().size();

        if (blockedPhrasesCount >= 1) {
            joinedPhrases = ChatFilter.getPhrases().get(0);
            for (int i = 1; i < blockedPhrasesCount; i++) {
                String phrase = ChatFilter.getPhrases().get(i);
                joinedPhrases = joinedPhrases.concat(", " + phrase);
            }
            Common.sendMessage(String.format("Blocked phrases (%d):", blockedPhrasesCount));
            Common.sendMessage(joinedPhrases);
        }
        else {
            Common.sendMessage("There are no blocked phrases");
        }
    }
}
