package me.cubert3d.palladium.module.setting.single;

import me.cubert3d.palladium.module.setting.SettingType;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "7/18/2021",
        type = ClassType.SETTING
)

public abstract class EnumSetting<E extends Enum<E>> extends SingleSetting<E> {

    public EnumSetting(final String name, final String description, E defaultValue) {
        super(name, description, defaultValue);
    }

    @Override
    public final SettingType getType() {
        return SettingType.ENUM;
    }

    @Override
    public String getAsString() {
        return getValue().toString();
    }
}
