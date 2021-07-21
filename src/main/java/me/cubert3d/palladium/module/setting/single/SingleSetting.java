package me.cubert3d.palladium.module.setting.single;

import me.cubert3d.palladium.module.setting.Setting;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.exception.SettingParseException;

import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "4/7/2021",
        type = ClassType.SETTING
)

public abstract class SingleSetting<T> extends Setting {

    private T value;
    private final T defaultValue;

    protected SingleSetting(final String name, final String description, T defaultValue) {
        super(name, description);
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }

    @Override
    public final String toString() {
        return value.toString();
    }

    @Override
    public final boolean isListSetting() {
        return false;
    }

    public final T getValue() {
        return value;
    }

    public final T getDefaultValue() {
        return defaultValue;
    }

    @Override
    public final void reset() {
        value = defaultValue;
    }

    protected void setValue(T value) {
        this.value = value;
    }

    @Override
    public void setFromString(String string) {
        Optional<T> optional = parseString(string);
        if (optional.isPresent()) {
            setValue(optional.get());
        }
        else {
            throw new SettingParseException();
        }
    }

    protected abstract Optional<T> parseString(String string);
}
