package me.cubert3d.palladium.module.setting.single;

import me.cubert3d.palladium.module.setting.SettingType;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/7/2021"
)

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
