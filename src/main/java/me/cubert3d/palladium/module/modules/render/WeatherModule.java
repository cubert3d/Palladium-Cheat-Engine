package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.event.callback.WeatherCallback;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.module.setting.single.EnumSetting;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "7/18/2021",
        type = ClassType.MODULE
)

public final class WeatherModule extends ToggleModule {

    private final WeatherSetting weatherSetting;

    public WeatherModule() {
        super("Weather", "Choose what weather to render.");
        this.weatherSetting = new WeatherSetting("Value", Setting.CLEAR);
        this.addSetting(weatherSetting);
    }

    @Override
    public void onLoad() {
        WeatherCallback.EVENT.register(() -> {
            if (isEnabled()) {
                return weatherSetting.getValue();
            }
            else {
                return Setting.NONE;
            }
        });
    }

    @Override
    public @NotNull Optional<String> getPrimaryInfo() {
        return Optional.of(weatherSetting.getAsString());
    }

    public enum Setting {
        NONE(0.15F, 0.0F),
        CLEAR(0.15F, 0.0F),
        RAIN(0.15F, 1.0F),
        SNOW(0.0F, 1.0F);

        private final float temperature;
        private final float gradient;

        Setting(float temperature, final float gradient) {
            this.temperature = temperature;
            this.gradient = gradient;
        }

        public final float getTemperature() {
            return temperature;
        }

        public final float getGradient() {
            return gradient;
        }

        public final Biome.Precipitation toPrecipitation() {
            switch (this) {
                case CLEAR: return Biome.Precipitation.NONE;
                case RAIN: return Biome.Precipitation.RAIN;
                case SNOW: return Biome.Precipitation.SNOW;
                default: return null;
            }
        }
    }

    private static final class WeatherSetting extends EnumSetting<Setting> {

        public WeatherSetting(String name, Setting defaultValue) {
            super(name, defaultValue);
        }

        @Override
        protected final Optional<Setting> parseString(@NotNull String string) {
            Setting setting;
            switch (string.toUpperCase()) {
                case "CLEAR": setting = Setting.CLEAR; break;
                case "RAIN": setting = Setting.RAIN; break;
                case "SNOW": setting = Setting.SNOW; break;
                default: setting = null;
            }
            return Optional.ofNullable(setting);
        }
    }
}
