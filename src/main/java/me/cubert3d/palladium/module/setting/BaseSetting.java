package me.cubert3d.palladium.module.setting;

import me.cubert3d.palladium.module.setting.single.*;
import me.cubert3d.palladium.module.setting.list.*;
import me.cubert3d.palladium.util.Named;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import org.jetbrains.annotations.Nullable;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/7/2021"
)

public abstract class BaseSetting implements Named {

    public static final String[] FORBIDDEN_SETTING_NAMES = new String[]{
            "enable",
            "disable",
            "toggle"
    };

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

    // CONVERSION

    public @Nullable BooleanSetting asBooleanSetting() {
        if (this.getType().equals(SettingType.BOOLEAN))
            return (BooleanSetting) this;
        else
            return null;
    }

    public @Nullable IntegerSetting asIntegerSetting() {
        if (this.getType().equals(SettingType.INTEGER))
            return (IntegerSetting) this;
        else
            return null;
    }

    public @Nullable DoubleSetting asDoubleSetting() {
        if (this.getType().equals(SettingType.DOUBLE))
            return (DoubleSetting) this;
        else
            return null;
    }

    public @Nullable StringSetting asStringSetting() {
        if (this.getType().equals(SettingType.STRING))
            return (StringSetting) this;
        else
            return null;
    }
}
