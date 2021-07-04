package me.cubert3d.palladium.module.modules.player;

import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.module.setting.single.DoubleSetting;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/14/2021",
        status = "in-progress"
)

public final class AutoDisconnectModule extends ToggleModule {
    public AutoDisconnectModule() {
        super("AutoDisconnect", "Disconnects you once you reach a certain health.",
                ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
        addSetting(new DoubleSetting("Threshold", 4.0, 19.0));
    }
}
