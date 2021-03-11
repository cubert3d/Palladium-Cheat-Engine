package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.event.callback.HungerCallback;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public class HungerManagerMixin {
    @Inject(at = @At(value = "TAIL"),
            method = "setFoodLevel(I)V")
    private void onHungerUpdate(int foodLevel, CallbackInfo info) {

        ActionResult result = HungerCallback.EVENT.invoker().interact(Common.getClientPlayer(), foodLevel);

        if (result.equals(ActionResult.FAIL))
            info.cancel();
    }
}
