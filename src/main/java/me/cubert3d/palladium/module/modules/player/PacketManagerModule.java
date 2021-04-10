package me.cubert3d.palladium.module.modules.player;

import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;

public final class PacketManagerModule extends Module {

    public PacketManagerModule() {
        super("PacketManager", "Displays incoming and outgoing packets on screen.",
                ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }

    @Override
    protected void onLoad() {

    }
}
