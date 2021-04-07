package me.cubert3d.palladium.module.modules.player;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.ChatFilter;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

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
    }

    @Override
    public void execute(String @NotNull [] args) {

        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "enable":
                    this.enable();
                    break;
                case "disable":
                    this.disable();
                    break;
                case "toggle":
                    this.toggle();
                    break;
                case "list":
                    this.listBlockedPhrases();
                    break;
            }
        }
        else if (args.length > 1) {

            String joined = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

            switch (args[0].toLowerCase()) {
                case "add":
                    ChatFilter.addPhrase(joined);
                    Common.sendMessage("Added \"" + joined + "\" to blocked phrases");
                    break;
                case "remove":
                    if (ChatFilter.removePhrase(joined))
                        Common.sendMessage("Removed \"" + joined + "\" from blocked phrases");
                    else
                        Common.sendMessage("Found no such phrase in list of blocked phrases");
                    break;
                case "has":
                    if (ChatFilter.contains(joined))
                        Common.sendMessage("\"" + joined + "\" is currently blocked");
                    else
                        Common.sendMessage("\"" + joined + "\" is not currently blocked");
                    break;
            }
        }
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
