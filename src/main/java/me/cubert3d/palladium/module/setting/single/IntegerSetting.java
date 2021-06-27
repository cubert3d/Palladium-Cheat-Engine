package me.cubert3d.palladium.module.setting.single;

import me.cubert3d.palladium.module.setting.SettingType;
import me.cubert3d.palladium.util.annotation.ClassDescription;

import java.util.Optional;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/7/2021"
)

public final class IntegerSetting extends NumberSetting<Integer> {

    public IntegerSetting(final String name, Integer defaultValue, int maxValue) {
        this(name, defaultValue, 0, maxValue);
    }

    public IntegerSetting(final String name, Integer defaultValue, int minValue, int maxValue) {
        super(name, defaultValue, minValue, maxValue);
    }

    @Override
    public final SettingType getType() {
        return SettingType.INTEGER;
    }

    @Override
    public final void setValue(Integer value) {
        value = Math.max(value, getMinValue());
        value = Math.min(value, getMaxValue());
        super.setValue(value);
    }

    @Override
    public final String getAsString() {
        return getValue().toString();
    }

    @Override
    protected final Optional<Integer> parseString(String string) {
        try {
            Integer integer = Integer.valueOf(string.trim());
            return Optional.of(integer);
        }
        catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
