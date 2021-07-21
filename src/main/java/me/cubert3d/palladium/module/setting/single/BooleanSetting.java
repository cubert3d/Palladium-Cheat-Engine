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

public final class BooleanSetting extends SingleSetting<Boolean> {

    public BooleanSetting(final String name, final String description, Boolean defaultValue) {
        super(name, description, defaultValue);
    }

    @Override
    public final SettingType getType() {
        return SettingType.BOOLEAN;
    }

    @Override
    public final void setValue(Boolean value) {
        super.setValue(value);
    }

    @Override
    public final String getAsString() {
        return getValue() ? "true" : "false";
    }

    @Override
    protected final Optional<Boolean> parseString(String string) {
        string = string.trim();
        if (string.equalsIgnoreCase("true")) {
            return Optional.of(true);
        }
        else if (string.equalsIgnoreCase("false")) {
            return Optional.of(false);
        }
        return Optional.empty();
    }
}
