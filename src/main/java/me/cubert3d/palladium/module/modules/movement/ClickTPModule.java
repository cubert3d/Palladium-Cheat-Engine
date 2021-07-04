package me.cubert3d.palladium.module.modules.movement;

import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.Common;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/11/2021",
        status = "in-progress"
)

public final class ClickTPModule extends ToggleModule {

    // Maximum distance, in blocks, that the player is allowed to teleport.
    private static final double maxDistance = 10.0;

    // Hand that triggers the teleportation when it is swung.
    public static final Hand HAND = Hand.MAIN_HAND;

    public ClickTPModule() {
        super("ClickTP", "Allows the player to teleport to where they click",
                ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }

    public final void teleport() {
        // TODO: clean up and document all this autism

        // Step 1:

        MinecraftClient client = Common.getMC();
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledWidth();

        if (client.cameraEntity == null)
            return;

        Vec3d cameraDirection = client.cameraEntity.getRotationVec(1.0F);

        double fov = client.options.fov;
        double angleSize = fov / height;

        Vector3f verticalRotationAxis = new Vector3f(cameraDirection);
        verticalRotationAxis.cross(Vector3f.POSITIVE_Y);

        if (!verticalRotationAxis.normalize())
            return;

        Vector3f horizontalRotationAxis = new Vector3f(cameraDirection);
        horizontalRotationAxis.cross(verticalRotationAxis);
        horizontalRotationAxis.normalize();

        verticalRotationAxis = new Vector3f(cameraDirection);
        verticalRotationAxis.cross(horizontalRotationAxis);

        // Step 2:

        Vec3d direction = map(
                (float) angleSize,
                cameraDirection,
                horizontalRotationAxis,
                verticalRotationAxis,
                (width/2), (height/2), width, height
        );

        HitResult hit = raycastInDirection(client, 1.0F, direction);

        if (hit == null)
            return;

        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hit;
            BlockPos blockPos = blockHitResult.getBlockPos();
            Common.sendMessage(String.format("Pos: %d, %d, %d", blockPos.getX(), blockPos.getY(), blockPos.getZ()));
            updatePosition(blockPos);
        }
    }

    @Contract("_, _, _, _, _, _, _, _ -> new")
    private static @NotNull Vec3d map(float anglePerPixel, Vec3d center,
                                      @NotNull Vector3f horizontalRotationalAxis, @NotNull Vector3f verticalRotationalAxis,
                                      int x, int y, int width, int height) {
        float horizontalRotation = (x - width/2f) * anglePerPixel;
        float verticalRotation = (y - height/2f) * anglePerPixel;

        final Vector3f temp2 = new Vector3f(center);
        temp2.rotate(verticalRotationalAxis.getDegreesQuaternion(verticalRotation));
        temp2.rotate(horizontalRotationalAxis.getDegreesQuaternion(horizontalRotation));
        return new Vec3d(temp2);
    }

    private static HitResult raycastInDirection(MinecraftClient client, float tickDelta, Vec3d direction) {
        Entity entity = client.getCameraEntity();
        if (entity == null || client.world == null || client.interactionManager == null) {
            return null;
        }

        double reachDistance = maxDistance;
        HitResult target = raycast(entity, reachDistance, tickDelta, false, direction);
        boolean tooFar = false;
        double extendedReach = reachDistance;

        Vec3d cameraPos = entity.getCameraPosVec(tickDelta);

        extendedReach = extendedReach * extendedReach;
        if (target != null) {
            extendedReach = target.getPos().squaredDistanceTo(cameraPos);
        }

        Vec3d vec3d3 = cameraPos.add(direction.multiply(reachDistance));
        Box box = entity.getBoundingBox()
                .stretch(entity.getRotationVec(1.0F).multiply(reachDistance))
                .expand(1.0, 1.0, 1.0);
        EntityHitResult entityHitResult = ProjectileUtil.raycast(
                entity,
                cameraPos,
                vec3d3,
                box,
                (entity1) -> !entity1.isSpectator() && entity1.collides(),
                extendedReach
        );

        if (entityHitResult == null)
            return target;

        Entity entity2 = entityHitResult.getEntity();
        Vec3d vec3d4 = entityHitResult.getPos();
        double g = cameraPos.squaredDistanceTo(vec3d4);

        if (g < extendedReach || target == null) {
            target = entityHitResult;
            if (entity2 instanceof LivingEntity || entity2 instanceof ItemFrameEntity) {
                client.targetedEntity = entity2;
            }
        }

        return target;
    }

    private static HitResult raycast(
            @NotNull Entity entity,
            double maxDistance,
            float tickDelta,
            boolean includeFields,
            @NotNull Vec3d direction
    ) {
        Vec3d end = entity.getCameraPosVec(tickDelta).add(direction.multiply(maxDistance));
        return entity.world.raycast(new RaycastContext(
                entity.getCameraPosVec(tickDelta),
                end,
                RaycastContext.ShapeType.OUTLINE,
                includeFields ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE,
                entity
        ));
    }

    private boolean updatePosition(BlockPos destination) {

        double x = destination.getX();
        double y = destination.getY() + 1;
        double z = destination.getZ();

        if (Common.getMC().getNetworkHandler() != null) {

            Common.getPlayer().updatePosition(x, y, z);
            
            return true;
        }
        else
            return false;
    }
}
