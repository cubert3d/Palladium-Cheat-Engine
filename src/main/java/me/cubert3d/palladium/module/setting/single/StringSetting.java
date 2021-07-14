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

public final class StringSetting extends SingleSetting<String> {

    public static final int MAX_STRING_LENGTH = 64;

    public StringSetting(final String name, String defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public boolean isSet() {
        return getValue().length() > 0;
    }

    @Override
    public final SettingType getType() {
        return SettingType.STRING;
    }

    @Override
    public final void setValue(String value) {
        value = value.trim();
        if (value.length() > MAX_STRING_LENGTH) {
            value = value.substring(0, MAX_STRING_LENGTH);
        }
        super.setValue(value);
    }

    @Override
    public String getAsString() {
        return getValue();
    }

    @Override
    public void setFromString(String string) {
        this.setValue(string);
    }

    @Override
    protected Optional<String> parseString(String string) {
        return Optional.of(string.trim());
    }
}
