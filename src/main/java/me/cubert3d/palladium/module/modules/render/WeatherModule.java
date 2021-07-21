package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.event.callback.WeatherCallback;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.module.setting.single.EnumDescribed;
import me.cubert3d.palladium.module.setting.single.EnumSetting;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumSet;
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
        this.weatherSetting = new WeatherSetting("Value", "Controls what weather is rendered.", SettingEnum.CLEAR);
        this.addSetting(weatherSetting);
    }

    @Override
    public void onLoad() {
        WeatherCallback.EVENT.register(() -> {
            if (isEnabled()) {
                return weatherSetting.getValue();
            }
            else {
                return SettingEnum.NONE;
            }
        });
    }

    @Override
    public @NotNull Optional<String> getPrimaryInfo() {
        return Optional.of(weatherSetting.getAsString());
    }

    private static final class WeatherSetting extends EnumSetting<SettingEnum> {

        public WeatherSetting(final String name, final String description, SettingEnum defaultValue) {
            super(name, description, defaultValue);
        }

        @Override
        public @NotNull ArrayList<String> getDescription() {
            ArrayList<String> lines = super.getDescription();
            for (SettingEnum setting : EnumSet.of(SettingEnum.CLEAR, SettingEnum.RAIN, SettingEnum.SNOW)) {
                lines.add(setting.toString() + ": " + setting.getDescription());
            }
            return lines;
        }

        @Override
        protected final Optional<SettingEnum> parseString(@NotNull String string) {
            SettingEnum setting;
            switch (string.toUpperCase()) {
                case "CLEAR": setting = SettingEnum.CLEAR; break;
                case "RAIN": setting = SettingEnum.RAIN; break;
                case "SNOW": setting = SettingEnum.SNOW; break;
                default: setting = null;
            }
            return Optional.ofNullable(setting);
        }
    }

    public enum SettingEnum implements EnumDescribed {
        NONE("Invalid Setting.", 0.15F, 0.0F),
        CLEAR("Render clear weather.", 0.15F, 0.0F),
        RAIN("Render rainy weather.", 0.15F, 1.0F),
        SNOW("Render snowy weather.", 0.0F, 1.0F);

        private final String description;
        private final float temperature;
        private final float gradient;

        SettingEnum(final String description, final float temperature, final float gradient) {
            this.description = description;
            this.temperature = temperature;
            this.gradient = gradient;
        }

        @Override
        public String getDescription() {
            return description;
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
}
