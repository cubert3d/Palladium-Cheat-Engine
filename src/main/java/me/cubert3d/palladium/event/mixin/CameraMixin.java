package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.modules.render.FreecamModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
abstract class CameraMixin {
}
