package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.modules.render.FreecamModule;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListEntry.class)
abstract class PlayerListEntryMixin {

    @Inject(method = "getGameMode()Lnet/minecraft/world/GameMode;", at = @At("HEAD"), cancellable = true)
    private void getGameModeInject(CallbackInfoReturnable<GameMode> info) {
        if (ModuleManager.isModuleEnabled(FreecamModule.class)) {
            //info.setReturnValue(GameMode.SPECTATOR);
        }
    }
}
