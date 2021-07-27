package me.cubert3d.palladium.module.modules.player;

import me.cubert3d.palladium.event.callback.CancelPacketCallback;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.network.packet.c2s.play.KeepAliveC2SPacket;

@ClassInfo(
        authors = "cubert3d",
        date = "3/10/2021",
        type = ClassType.MODULE
)

public final class BlinkModule extends ToggleModule {

    public BlinkModule() {
        super("Blink", "Blocks communication with the server.");
    }

    @Override
    public void onLoad() {
        CancelPacketCallback.EVENT.register(packet -> isEnabled() && !(packet instanceof KeepAliveC2SPacket));
    }
}
