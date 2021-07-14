package me.cubert3d.palladium.module.modules.movement;

import me.cubert3d.palladium.event.callback.EntityControlCallback;
import me.cubert3d.palladium.module.modules.ToggleModule;
import net.minecraft.util.ActionResult;

public final class EntityControlModule extends ToggleModule {

    public EntityControlModule() {
        super("EntityControl", "Allows the player to control horses.");
    }

    @Override
    public void onLoad() {
        EntityControlCallback.EVENT.register(() -> {
            if (isEnabled()) {
                return ActionResult.SUCCESS;
            }
            else {
                return ActionResult.PASS;
            }
        });
    }
}
