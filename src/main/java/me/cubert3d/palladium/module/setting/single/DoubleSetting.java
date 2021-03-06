package me.cubert3d.palladium.module.setting.single;

import me.cubert3d.palladium.module.setting.SettingType;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "4/7/2021",
        type = ClassType.SETTING
)

public final class DoubleSetting extends NumberSetting<Double> {

    public DoubleSetting(final String name, final String description, Double defaultValue, double maxValue) {
        super(name, description, defaultValue, 0.0, maxValue);
    }

    public DoubleSetting(final String name, final String description, Double defaultValue, double minValue, double maxValue) {
        super(name, description, defaultValue, minValue, maxValue);
    }

    @Override
    public final SettingType getType() {
        return SettingType.DOUBLE;
    }

    @Override
    public final void setValue(Double value) {
        value = Math.max(value, getMinValue());
        value = Math.min(value, getMaxValue());
        super.setValue(value);
    }

    @Override
    public final String getAsString() {
        return getValue().toString();
    }

    @Override
    protected final Optional<Double> parseString(String string) {
        try {
            Double d = Double.valueOf(string.trim());
            return Optional.of(d);
        }
        catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
