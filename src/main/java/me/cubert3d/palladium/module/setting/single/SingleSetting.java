package me.cubert3d.palladium.module.setting.single;

import me.cubert3d.palladium.module.setting.BaseSetting;

public abstract class SingleSetting<T> extends BaseSetting {

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
}
