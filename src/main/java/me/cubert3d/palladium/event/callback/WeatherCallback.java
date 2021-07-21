package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.event.mixin.mixins.WorldMixin;
import me.cubert3d.palladium.event.mixin.mixins.WorldRendererMixin;
import me.cubert3d.palladium.module.modules.render.WeatherModule;
import me.cubert3d.palladium.util.annotation.CallbackInfo;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.annotation.Interaction;
import me.cubert3d.palladium.util.annotation.Listener;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@ClassInfo(
        authors = "cubert3d",
        date = "7/18/2021",
        type = ClassType.CALLBACK
)

@CallbackInfo(
        returns = WeatherModule.Setting.class,
        listeners = {
                @Listener(where = WeatherModule.class)
        },
        interactions = {
                @Interaction(where = WorldRendererMixin.class, method = {"getPrecipitationRedirect", "getTemperatureRedirect", "tickRainSplashingInject"}),
                @Interaction(where = WorldMixin.class, method = "getRainGradientInject")
        }
)

public interface WeatherCallback {

    Event<WeatherCallback> EVENT = EventFactory.createArrayBacked(WeatherCallback.class,
            listeners -> () -> {
                for (WeatherCallback listener : listeners) {
                    WeatherModule.Setting setting = listener.check();
                    if (setting != WeatherModule.Setting.NONE) {
                        return setting;
                    }
                }
                return WeatherModule.Setting.NONE;
            });

    WeatherModule.Setting check();
}
