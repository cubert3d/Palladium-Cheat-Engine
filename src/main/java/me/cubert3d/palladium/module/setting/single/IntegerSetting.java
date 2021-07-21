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

public final class IntegerSetting extends NumberSetting<Integer> {

    public IntegerSetting(final String name, final String description, Integer defaultValue, int maxValue) {
        this(name, description, defaultValue, 0, maxValue);
    }

    public IntegerSetting(final String name, final String description, Integer defaultValue, int minValue, int maxValue) {
        super(name, description, defaultValue, minValue, maxValue);
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
