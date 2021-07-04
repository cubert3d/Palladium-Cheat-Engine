package me.cubert3d.palladium.module.modules.player;

import me.cubert3d.palladium.event.callback.ChatFilterCallback;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.module.setting.list.StringListSetting;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.util.ActionResult;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/12/2021"
)

public final class ChatFilterModule extends ToggleModule {

    public ChatFilterModule() {
        super("ChatFilter", "Blocks any messages that contain a blocked phrase.",
                ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
        this.addSetting(new StringListSetting("Blacklist"));
    }

    @Override
    protected void onLoad() {
        ChatFilterCallback.EVENT.register(message -> {

            message = message.trim().toLowerCase();

            if (this.isEnabled()) {
                for (String phrase : this.getSetting("Blacklist").asStringListSetting().getList()) {
                    if (message.contains(phrase.trim().toLowerCase())) {
                        return ActionResult.FAIL;
                    }
                }
            }
            return ActionResult.PASS;
        });
    }
}
