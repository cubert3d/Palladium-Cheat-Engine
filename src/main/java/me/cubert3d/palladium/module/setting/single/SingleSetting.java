package me.cubert3d.palladium.module.setting.single;

import me.cubert3d.palladium.module.setting.Setting;
import me.cubert3d.palladium.util.annotation.ClassDescription;

import java.io.IOException;
import java.util.Optional;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/7/2021"
)

public abstract class SingleSetting<T> extends Setting {

    private T value;
    private final T defaultValue;

    protected SingleSetting(final String name, T defaultValue) {
        super(name);
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
    public final void setFromString(String string) throws IOException {
        Optional<T> optional = parseString(string);
        if (optional.isPresent()) {
            setValue(optional.get());
        }
        else {
            throw new IOException();
        }
    }

    protected abstract Optional<T> parseString(String string);
}
