package me.cubert3d.palladium.module.setting.single;

import me.cubert3d.palladium.module.setting.SettingType;

public final class IntegerSetting extends SingleSetting<Integer> {

    public IntegerSetting(final String name, Integer defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public final SettingType getType() {
        return SettingType.INTEGER;
    }

    @Override
    public final void setValue(Integer value) {
        super.setValue(value);
    }
}
