package me.cubert3d.palladium.module.setting.single;

import me.cubert3d.palladium.module.setting.SettingType;
import me.cubert3d.palladium.util.annotation.ClassDescription;

import java.util.Optional;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/7/2021"
)

public final class StringSetting extends SingleSetting<String> {

    public static final int MAX_STRING_LENGTH = 64;

    public StringSetting(final String name, String defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public final SettingType getType() {
        return SettingType.STRING;
    }

    @Override
    public final void setValue(String value) {
        value = value.trim().substring(0, MAX_STRING_LENGTH);
        super.setValue(value);
    }

    @Override
    public String getAsString() {
        return getValue();
    }

    @Override
    protected Optional<String> parseString(String string) {
        return Optional.of(string.trim());
    }
}
