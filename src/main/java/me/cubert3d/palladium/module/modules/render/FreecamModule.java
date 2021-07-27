package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.event.callback.CancelPacketCallback;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

@ClassInfo(
        authors = "cubert3d",
        date = "6/26/2021",
        type = ClassType.MODULE
)

public final class FreecamModule extends ToggleModule {

    private double lastX, lastY, lastZ;

    public FreecamModule() {
        super("Freecam", "Allows you to move your camera freely, as if in spectator mode.");
    }

    @Override
    public final void onLoad() {
        CancelPacketCallback.EVENT.register(packet -> isEnabled() && packet instanceof PlayerMoveC2SPacket);
    }

    @Override
    protected final void onEnable() {
        copyPos();
        setFlying(true);
    }

    @Override
    protected final void onDisable() {
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
