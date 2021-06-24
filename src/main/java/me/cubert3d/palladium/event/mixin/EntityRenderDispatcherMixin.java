package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.modules.render.ESPModule;
import me.cubert3d.palladium.util.ColorF;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.MinecraftClient;
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
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.OptionalDouble;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "6/22/2021",
        status = "in-progress"
)

@Mixin(EntityRenderDispatcher.class)
abstract class EntityRenderDispatcherMixin {

    private static final RenderLayer ESP_LINES = RenderLayer.of("esp", VertexFormats.POSITION_COLOR, 1, 256,
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
            at = @At("HEAD"))
    private <E extends Entity> void renderInject(E entity, double x, double y, double z, float yaw,
                                                 float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                                 int light, CallbackInfo info) {

        if (!ModuleManager.isModuleEnabled(ESPModule.class)) {
            return;
        }

        if (!entity.equals(MinecraftClient.getInstance().player)) {
            try {
                EntityRenderer entityRenderer = ((EntityRenderDispatcher)(Object)this).getRenderer(entity);
                Vec3d vec3d = entityRenderer.getPositionOffset(entity, tickDelta);
                double d = x + vec3d.getX();
                double e = y + vec3d.getY();
                double f = z + vec3d.getZ();
                matrices.push();
                matrices.translate(d, e, f);

                VertexConsumer vertices = vertexConsumers.getBuffer(ESP_LINES);
                Box box = entity.getBoundingBox().offset(-entity.getX(), -entity.getY(), -entity.getZ());
                ColorF color = getBoxColor(entity);
                WorldRenderer.drawBox(matrices, vertices, box, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

                matrices.pop();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ColorF getBoxColor(Entity entity) {
        /*
        GREEN = PASSIVE
        BLUE = NEUTRAL
        RED = HOSTILE
        WHITE = MISCELLANEOUS
         */
        if (entity instanceof LivingEntity) {
            if (entity instanceof Angerable) {
                return ColorF.BLUE;
            }
            else if (entity instanceof PassiveEntity) {
                return ColorF.GREEN;
            }
            else if (entity instanceof MobEntity) {
                return ColorF.RED;
            }
        }
        return ColorF.WHITE;
    }
}
