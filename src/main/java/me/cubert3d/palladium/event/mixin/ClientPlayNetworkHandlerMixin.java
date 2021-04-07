package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.setting.Setting;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/10/2021",
        status = "complete"
)

@Mixin(ClientPlayNetworkHandler.class)
public final class ClientPlayNetworkHandlerMixin {

    @Inject(at = @At(value = "INVOKE"),
            method = "sendPacket(Lnet/minecraft/network/Packet;)V",
            cancellable = true)
    private void onSendPacket(Packet<?> packet, final CallbackInfo info) {
        if (ModuleManager.getModule("Blink").get().isEnabled()) {
            info.cancel();
        }
    }

    @Inject(at = @At(value = "TAIL"),
            method = "onHealthUpdate(Lnet/minecraft/network/packet/s2c/play/HealthUpdateS2CPacket;)V")
    private void onHealthUpdate(@NotNull HealthUpdateS2CPacket packet, final CallbackInfo info) {

        double health = packet.getHealth();
        Optional<Module> optionalModule = ModuleManager.getModule("AutoDisconnect");

        optionalModule.ifPresent(module -> {
            Optional<Setting> optionalSetting = module.getSetting("Threshold");
            optionalSetting.ifPresent(setting -> {
                double threshold = (double) setting.getValue();
                if (health <= threshold) {
                    Common.getMC().disconnect();
                }
            });
        });
    }
}
