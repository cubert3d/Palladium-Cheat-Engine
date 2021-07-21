package me.cubert3d.palladium.module.modules.movement;

import me.cubert3d.palladium.event.callback.KeyPressedCallback;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;

@ClassInfo(
        authors = "cubert3d",
        date = "7/16/2021",
        type = ClassType.MODULE
)

public final class AutoWalkModule extends ToggleModule {

    public AutoWalkModule() {
        super("AutoWalk", "Makes the player automatically walk forward.");
    }

    @Override
    public final void onLoad() {
        KeyPressedCallback.EVENT.register(binding -> isEnabled() && binding.equals(MinecraftClient.getInstance().options.keyForward));
    }
}
