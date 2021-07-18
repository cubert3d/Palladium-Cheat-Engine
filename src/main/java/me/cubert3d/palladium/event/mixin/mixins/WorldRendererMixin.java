package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.event.callback.WeatherCallback;
import me.cubert3d.palladium.module.modules.render.WeatherModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassInfo(
        authors = "cubert3d",
        date = "7/18/2021",
        type = ClassType.MIXIN
)

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Redirect(method = "renderWeather",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;getPrecipitation()Lnet/minecraft/world/biome/Biome$Precipitation;"))
    private Biome.Precipitation getPrecipitationRedirect(Biome biome) {
        WeatherModule.Setting setting = WeatherCallback.EVENT.invoker().check();
        if (!setting.equals(WeatherModule.Setting.NONE)) {
            return setting.toPrecipitation();
        }
        else {
            return biome.getPrecipitation();
        }
    }

    @Redirect(method = "renderWeather",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;getTemperature(Lnet/minecraft/util/math/BlockPos;)F"))
    private float getTemperatureRedirect(Biome biome, BlockPos blockPos) {
        WeatherModule.Setting setting = WeatherCallback.EVENT.invoker().check();
        if (!setting.equals(WeatherModule.Setting.NONE)) {
            return setting.getTemperature();
        }
        else {
            return biome.getTemperature(blockPos);
        }
    }

    @Inject(method = "tickRainSplashing(Lnet/minecraft/client/render/Camera;)V", at = @At("HEAD"), cancellable = true)
    private void tickRainSplashingInject(Camera camera, CallbackInfo info) {
        WeatherModule.Setting setting = WeatherCallback.EVENT.invoker().check();
        if (!setting.equals(WeatherModule.Setting.NONE) && !setting.equals(WeatherModule.Setting.RAIN)) {
            info.cancel();
        }
    }
}
