package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.module.modules.ToggleModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public final class FreecamModule extends ToggleModule {

    double lastX, lastY, lastZ;

    public FreecamModule() {
        super("Freecam", "Allows you to move your camera freely, as if in spectator mode.", ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }

    @Override
    protected void onEnable() {
        copyPos();
        setFlying(true);
    }

    @Override
    protected void onDisable() {
        setFlying(false);
        resetPos();
    }

    private void copyPos() {
        lastX = getPlayer().getX();
        lastY = getPlayer().getY();
        lastZ = getPlayer().getZ();
    }

    private void resetPos() {
        getPlayer().setPos(lastX, lastY, lastZ);
    }

    private void setFlying(boolean flying) {
        getPlayer().abilities.allowFlying = flying;
        getPlayer().abilities.flying = flying;
    }

    private ClientPlayerEntity getPlayer() {
        return MinecraftClient.getInstance().player;
    }
}
