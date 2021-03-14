package me.cubert3d.palladium.module.setting;

import me.cubert3d.palladium.util.Named;
import me.cubert3d.palladium.util.annotation.ClassDescription;

/*
This is the base class for all Setting classes. A Setting is essentially
a named value, and it can be referenced by its name.
 */

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/4/2021",
        status = "complete"
)

public abstract class AbstractSetting<T> implements Named {

    private final String name;
    private final T defaultValue;
    private T value;

    protected AbstractSetting(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    @Override
    public final String getName() {
        return name;
    }

    public final T getDefaultValue() {
        return defaultValue;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    // Resets the value of this setting to the default value, and then returns whatever it was set to previously.
    public T resetValue() {
        T previousValue = getValue();
        setValue(getDefaultValue());
        return previousValue;
    }
}
