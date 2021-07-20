package me.cubert3d.palladium.module.modules.combat;

import me.cubert3d.palladium.event.callback.KillAuraCallback;
import me.cubert3d.palladium.event.callback.SendPacketCallback;
import me.cubert3d.palladium.event.mixin.accessors.ClientPlayNetworkHandlerAccessor;
import me.cubert3d.palladium.event.mixin.accessors.MinecraftClientAccessor;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

@ClassInfo(
        authors = "cubert3d",
        date = "7/18/2021",
        type = ClassType.MODULE
)

public final class KillAuraModule extends ToggleModule {

    private Entity nextTarget;
    private double lastTargetYaw;
    private double lastTargetPitch;
    private PlayerMoveC2SPacket.LookOnly lastLookPacketSent;

    public KillAuraModule() {
        super("KillAura", "Makes the player automatically swing at nearby enemies.");
    }

    @Override
    public void onLoad() {

        KillAuraCallback.EVENT.register(() -> {
            ClientWorld world = MinecraftClient.getInstance().world;
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();
            if (isEnabled() && world != null && player != null && networkHandler != null) {
                if (nextTarget == null || !isTargetCloseEnough(player, nextTarget)) {
                    setNextTarget(world, player);
                }
                if (nextTarget != null) {
                    if (!isInView(player, nextTarget)) {
                        lookTowardsTarget(player, networkHandler);
                    }
                    if (shouldAttack(player)) {
                        attackTarget(world, player);
                    }
                }
            }
        });

        SendPacketCallback.EVENT.register(packet -> {
            if (packet instanceof PlayerMoveC2SPacket.LookOnly) {
                if (isEnabled()) {
                    return true;
                }
                else {
                    lastLookPacketSent = (PlayerMoveC2SPacket.LookOnly) packet;
                }
            }
            return false;
        });
    }

    private boolean shouldAttack(ClientPlayerEntity player) {
        return ((MinecraftClientAccessor) MinecraftClient.getInstance()).getAttackCooldown() <= 0
                && player.getAttackCooldownProgress(0.5F) >= 1.0F
                && isInView(player, nextTarget);
    }

    private void attackTarget(ClientWorld world, ClientPlayerEntity player) {
        ClientPlayerInteractionManager interactionManager = MinecraftClient.getInstance().interactionManager;
        if (interactionManager != null) {
            interactionManager.attackEntity(MinecraftClient.getInstance().player, nextTarget);
            setNextTarget(world, player);
        }
    }

    private void setNextTarget(@NotNull ClientWorld world, ClientPlayerEntity player) {
        List<Entity> entities = new LinkedList<>();
        for (Entity entity : world.getEntities()) {
            if (filterTarget(player, entity)) {
                entities.add(entity);
            }
        }
        if (entities.size() > 0) {
            nextTarget = entities.get(0);
        }
        else {
            nextTarget = null;
        }
    }

    private boolean filterTarget(ClientPlayerEntity player, Entity target) {
        return !(target instanceof ItemEntity)
                && !(target instanceof ExperienceOrbEntity)
                && !(target instanceof PersistentProjectileEntity)
                && player != null
                && !target.equals(player)
                && isTargetCloseEnough(player, target);
    }

    private boolean isTargetCloseEnough(@NotNull ClientPlayerEntity player, Entity target) {
        return player.squaredDistanceTo(target) < 9.0D;
    }

    private boolean isInView(@NotNull ClientPlayerEntity player, Entity target) {
        if (target != null && lastLookPacketSent != null) {

            // Get the difference in yaw.
            double playerYaw = getPlayerYaw(player);
            double targetYaw = getTargetYaw(player, target);
            double diffInYaw = Math.abs(playerYaw - targetYaw);

            // Get the difference in pitch.
            double playerPitch = getPlayerPitch(player);
            double targetPitch = getTargetPitch(player, target);
            double diffInPitch = Math.abs(playerPitch - targetPitch);

            lastTargetYaw = targetYaw;
            lastTargetPitch = targetPitch;

            boolean yawCloseEnough = diffInYaw < 45;
            boolean pitchCloseEnough = diffInPitch < 45;

            return yawCloseEnough && pitchCloseEnough;
        }
        else {
            return false;
        }
    }

    // Returns degrees.
    private double getPlayerYaw(ClientPlayerEntity player) {
        if (lastLookPacketSent != null) {
            double packetYaw = lastLookPacketSent.getYaw(0.0F);
            while (packetYaw > 360.0) {
                packetYaw -= 360.0;
            }
            return packetYaw;
        }
        else {
            Vec3d rotation = player.getRotationVector();
            double playerYaw = Math.atan2(rotation.getX(), rotation.getZ());
            return playerYaw * 180/Math.PI;
        }
    }

    // Returns degrees.
    private double getTargetYaw(@NotNull ClientPlayerEntity player, @NotNull Entity target) {
        Vec3d position = player.getPos();
        Vec3d targetRelPosition = target.getPos().subtract(position);
        double targetYaw = Math.atan2(targetRelPosition.getX(), targetRelPosition.getZ()) * 180/Math.PI;
        // The target yaw has to be reoriented to match the packet yaw.
        // Add 360 degrees to make south return 0 degrees, just like packet yaw.
        targetYaw += 360.0;
        // After that addition, the target yaw is liable to be greater than 360 degrees; if it is, find a coterminal angle.
        while (targetYaw > 360.0) {
            targetYaw -= 360.0;
        }
        // Change target yaw from counter-clockwise to clockwise, like packet yaw.
        targetYaw = Math.abs(targetYaw - 360);
        return targetYaw;
    }

    // Returns degrees.
    private double getPlayerPitch(ClientPlayerEntity player) {
        if (lastLookPacketSent != null) {
            return lastLookPacketSent.getPitch(0.0F);
        }
        else {
            Vec3d rotation = player.getRotationVector();
            double playerPitch = Math.asin(rotation.getY());
            return playerPitch * 180/Math.PI;
        }
    }

    // Returns degrees.
    private double getTargetPitch(@NotNull ClientPlayerEntity player, @NotNull Entity target) {
        Vec3d position = player.getPos();
        Vec3d targetRelPosition = target.getPos().subtract(position);
        double verticalDistance = targetRelPosition.getY();
        double horizontalPlaneDistance = Math.sqrt(Math.abs(Math.pow(targetRelPosition.getX(), 2) + Math.pow(targetRelPosition.getZ(), 2)));
        double targetPitch = Math.atan2(verticalDistance, horizontalPlaneDistance);
        // Packet pitch has opposite sign.
        targetPitch *= -1;
        return targetPitch * 180/Math.PI;
    }

    private void lookTowardsTarget(@NotNull ClientPlayerEntity player, ClientPlayNetworkHandler networkHandler) {
        PlayerMoveC2SPacket.LookOnly packet = new PlayerMoveC2SPacket.LookOnly((float) lastTargetYaw, (float) lastTargetPitch, player.isOnGround());
        ((ClientPlayNetworkHandlerAccessor) networkHandler).getConnection().send(packet);
        lastLookPacketSent = packet;
    }
}
