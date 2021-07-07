package me.cubert3d.palladium.module.modules.player;

import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.modules.ToggleModule;

public final class PacketManagerModule extends ToggleModule {

    public PacketManagerModule() {
        super("PacketManager", "Displays incoming and outgoing packets on screen.", ModuleDevStatus.DEBUG_ONLY);
    }

    @Override
    public void onLoad() {

    }
}
