package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.modules.render.ChamsModule;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderDispatcher.class)
public final class EntityRenderDispatcherMixin {
    @Inject(method = "shouldRender(" +
            "Lnet/minecraft/entity/Entity;" +
            "Lnet/minecraft/client/render/Frustum;" +
            "DDD" +
            ")Z",
            at = @At("HEAD"), cancellable = true)
    private <E extends Entity> void onShouldRender(E entity, Frustum frustum, double x, double y, double z,
                                                   CallbackInfoReturnable<Boolean> info) {
        if (ModuleManager.isModuleEnabled(ChamsModule.class)) {
            if (entity.getType().equals(EntityType.VILLAGER))
                Common.sendMessage("Chams Test: " + info.getReturnValueZ());
            info.setReturnValue(true);
        }
    }
}
