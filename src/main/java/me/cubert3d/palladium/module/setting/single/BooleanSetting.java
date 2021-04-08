package me.cubert3d.palladium.module.setting.single;

import me.cubert3d.palladium.module.setting.SettingType;

public final class BooleanSetting extends SingleSetting<Boolean> {

    public BooleanSetting(final String name, Boolean defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public final SettingType getType() {
        return SettingType.BOOLEAN;
    }

    @Override
    public final void setValue(Boolean value) {
        super.setValue(value);
    }
}
