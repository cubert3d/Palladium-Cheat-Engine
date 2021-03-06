package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.callback.EntityRenderCallback;
import me.cubert3d.palladium.event.mixin.MixinCaster;
import me.cubert3d.palladium.event.mixin.accessors.RenderPhaseAccessor;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.render.Color4F;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.OptionalDouble;

@ClassInfo(
        authors = "cubert3d",
        date = "6/22/2021",
        type = ClassType.MIXIN
)

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin implements MixinCaster<EntityRenderDispatcher> {

    private static final RenderLayer ESP_LAYER = RenderLayer.of("esp", VertexFormats.POSITION_COLOR, 1, 256,
            RenderLayer.MultiPhaseParameters.builder().lineWidth(new RenderPhase
                    .LineWidth(OptionalDouble.empty()))
                    .depthTest(RenderPhaseAccessor.getAlwaysDepthTest())
                    .layering(RenderPhaseAccessor.getNoLayering())
                    .transparency(RenderPhaseAccessor.getNoTransparency())
                    .target(RenderPhaseAccessor.getItemTarget())
                    .writeMaskState(RenderPhaseAccessor.getAllMask())
                    .build(false));

    @Inject(method = "render(" +
            "Lnet/minecraft/entity/Entity;" +
            "DDDFF" +
            "Lnet/minecraft/client/util/math/MatrixStack;" +
            "Lnet/minecraft/client/render/VertexConsumerProvider;" +
            "I" +
            ")V",
            at = @At("TAIL"))
    private <E extends Entity> void renderInject(E entity, double x, double y, double z, float yaw,
                                                 float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                                 int light, final CallbackInfo info) {

        boolean drawESP = EntityRenderCallback.EVENT.invoker().shouldDrawESP(entity);
        if (drawESP) {
            try {
                EntityRenderer entityRenderer = self().getRenderer(entity);
                Vec3d vec3d = entityRenderer.getPositionOffset(entity, tickDelta);
                double d = x + vec3d.getX();
                double e = y + vec3d.getY();
                double f = z + vec3d.getZ();
                matrices.push();
                matrices.translate(d, e, f);

                VertexConsumer vertices = vertexConsumers.getBuffer(ESP_LAYER);
                Box box = entity.getBoundingBox().offset(-entity.getX(), -entity.getY(), -entity.getZ());
                Color4F color = getEntityColor(entity);
                WorldRenderer.drawBox(matrices, vertices, box, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

                matrices.pop();
            }
            catch (Exception e) {
                Palladium.getLogger().error("Error rendering ESP");
            }
        }
    }

    private Color4F getEntityColor(Entity entity) {
        /*
        GREEN = PASSIVE
        BLUE = NEUTRAL
        RED = HOSTILE
        YELLOW = PLAYER
        WHITE = MISCELLANEOUS
         */
        if (entity instanceof PlayerEntity) {
            return Color4F.YELLOW;
        }
        else if (entity instanceof LivingEntity) {
            if (entity instanceof Angerable) {
                return Color4F.BLUE;
            }
            else if (entity instanceof PassiveEntity) {
                return Color4F.GREEN;
            }
            else if (entity instanceof MobEntity) {
                return Color4F.RED;
            }
        }
        return Color4F.WHITE;
    }
}
