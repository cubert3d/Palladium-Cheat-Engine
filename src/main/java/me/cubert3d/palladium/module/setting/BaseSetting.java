package me.cubert3d.palladium.module.setting;

import me.cubert3d.palladium.module.setting.single.IntegerSetting;
import me.cubert3d.palladium.util.Named;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/7/2021"
)

public abstract class BaseSetting implements Named {

    private final String name;

    protected BaseSetting(final String name) {
        this.name = name;
    }

    @Override
    public final String getName() {
        return name;
    }

    public abstract SettingType getType();

    public abstract boolean isListSetting();

    public abstract void reset();

    public IntegerSetting asIntegerSetting() {
        if (this instanceof IntegerSetting)
            return (IntegerSetting) this;
        else
            return null;
    }
}
