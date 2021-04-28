package me.cubert3d.palladium.event.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
abstract class EntityRenderDispatcherMixin {

    /*
    @Inject(method = "shouldRender(" +
            "Lnet/minecraft/entity/Entity;" +
            "Lnet/minecraft/client/render/Frustum;" +
            "DDD" +
            ")Z",
            at = @At("HEAD"), cancellable = true)
    private <E extends Entity> void shouldRenderInject(E entity, Frustum frustum, double x, double y, double z,
                                                   CallbackInfoReturnable<Boolean> info) {
        if (ModuleManager.isModuleEnabled(ChamsModule.class)) {
            if (entity.getType().equals(EntityType.VILLAGER))
                Common.sendMessage("Chams Test: " + info.getReturnValueZ());
            info.setReturnValue(true);
        }
    }
     */

    /*
    @Inject(method = "render(" +
            "Lnet/minecraft/entity/Entity;" +
            "DDDFF" +
            "Lnet/minecraft/client/util/math/MatrixStack;" +
            "Lnet/minecraft/client/render/VertexConsumerProvider;" +
            "I" +
            ")V",
            at = @At(""))
    private <E extends Entity> void renderInject(E entity, double x, double y, double z, float yaw,
                                                 float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                                 int light, CallbackInfo info) {

    }

     */
}
