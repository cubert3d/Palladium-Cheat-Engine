package me.cubert3d.palladium.module.setting.single;

import me.cubert3d.palladium.module.setting.SettingType;

public final class DoubleSetting extends SingleSetting<Double> {

    public DoubleSetting(final String name, Double defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public final SettingType getType() {
        return SettingType.DOUBLE;
    }

    @Override
    public final void setValue(Double value) {
        super.setValue(value);
    }
}
