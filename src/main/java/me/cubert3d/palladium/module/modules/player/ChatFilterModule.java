package me.cubert3d.palladium.module.modules.player;

import me.cubert3d.palladium.module.AbstractModule;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/12/2021",
        status = "complete"
)

public final class ChatFilterModule extends AbstractModule {
        public ChatFilterModule() {
                super("ChatFilter", "Blocks any messages that contain a blocked phrase.",
                        ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
        }
}
