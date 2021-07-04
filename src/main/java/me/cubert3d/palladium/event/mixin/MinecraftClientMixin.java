package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.Configuration;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
abstract class MinecraftClientMixin {
    @Inject(method = "close()V", at = @At("HEAD"))
    private void closeInject(CallbackInfo info) {
        Configuration.saveConfig();
    }
}
