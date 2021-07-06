package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.event.callback.HungerCallback;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/4/2021",
        status = "complete"
)

@Mixin(HungerManager.class)
abstract class HungerManagerMixin {

    @Inject(at = @At(value = "TAIL"),
            method = "setFoodLevel(I)V")
    private void setFoodLevelInject(int foodLevel, final CallbackInfo info) {

        ActionResult result = HungerCallback.EVENT.invoker().interact(MinecraftClient.getInstance().player, foodLevel);

        if (result.equals(ActionResult.FAIL))
            info.cancel();
    }
}
