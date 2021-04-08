package me.cubert3d.palladium.module.setting.single;

import me.cubert3d.palladium.module.setting.SettingType;
import me.cubert3d.palladium.util.annotation.ClassDescription;

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
}
