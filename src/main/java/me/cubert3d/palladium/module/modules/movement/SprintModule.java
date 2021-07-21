package me.cubert3d.palladium.module.modules.movement;

import me.cubert3d.palladium.event.callback.KeyPressedCallback;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;

@ClassInfo(
        authors = "cubert3d",
        date = "3/10/2021",
        type = ClassType.MODULE
)

public final class SprintModule extends ToggleModule {

    public SprintModule() {
        super("Sprint", "Makes the player sprint.");
    }

    @Override
    public final void onLoad() {
        KeyPressedCallback.EVENT.register(binding -> isEnabled() && binding.equals(MinecraftClient.getInstance().options.keySprint));
    }
}
