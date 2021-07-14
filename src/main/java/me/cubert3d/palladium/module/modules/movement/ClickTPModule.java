package me.cubert3d.palladium.module.modules.movement;

import me.cubert3d.palladium.event.callback.ClickTPRaycastCallback;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/11/2021",
        status = "in-progress"
)

public final class ClickTPModule extends ToggleModule {

    public ClickTPModule() {
        super("ClickTP", "Allows the player to teleport to where they click");
    }

    @Override
    public void onLoad() {
        ClickTPRaycastCallback.EVENT.register(hand -> {
            if (isEnabled()) {
                HitResult crosshairTarget = MinecraftClient.getInstance().crosshairTarget;
                if (crosshairTarget != null && crosshairTarget.getType().equals(HitResult.Type.BLOCK)) {
                    updatePosition(crosshairTarget.getPos());
                }
            }
            return ActionResult.PASS;
        });
    }

    private boolean updatePosition(Vec3d destination) {

        double x = destination.getX();
        double y = destination.getY();
        double z = destination.getZ();
        ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();

        if (networkHandler != null) {

            PlayerMoveC2SPacket.PositionOnly packet = new PlayerMoveC2SPacket.PositionOnly(x, y, z, true);
            networkHandler.sendPacket(packet);
            MinecraftClient.getInstance().player.updatePosition(x, y, z);

            return true;
        }
        else {
            return false;
        }
    }
}
