package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.event.callback.WeatherCallback;
import me.cubert3d.palladium.module.modules.render.WeatherModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClassInfo(
        authors = "cubert3d",
        date = "7/18/2021",
        type = ClassType.MIXIN
)

@Mixin(World.class)
public abstract class WorldMixin {

    @Inject(method = "getRainGradient(F)F", at = @At("HEAD"), cancellable = true)
    private void getRainGradientInject(float delta, CallbackInfoReturnable<Float> info) {
        WeatherModule.SettingEnum setting = WeatherCallback.EVENT.invoker().check();
        if (!setting.equals(WeatherModule.SettingEnum.NONE) && MinecraftClient.getInstance().world != null) {
            info.setReturnValue(setting.getGradient());
        }
    }
}
