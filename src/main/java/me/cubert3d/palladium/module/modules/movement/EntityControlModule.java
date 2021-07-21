package me.cubert3d.palladium.module.modules.movement;

import me.cubert3d.palladium.event.callback.EntityControlCallback;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "7/9/2021",
        type = ClassType.MODULE
)

public final class EntityControlModule extends ToggleModule {

    public EntityControlModule() {
        super("EntityControl", "Allows the player to control horses.");
    }

    @Override
    public void onLoad() {
        EntityControlCallback.EVENT.register(this::isEnabled);
    }
}
