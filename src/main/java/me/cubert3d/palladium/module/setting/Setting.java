package me.cubert3d.palladium.module.setting;

import me.cubert3d.palladium.util.Conversion;
import me.cubert3d.palladium.util.Named;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

// fuck generics, "capture of ?" my ass.
// this whole class is a disgrace
// a day in the life of an OOP programmer

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/13/2021",
        status = "in-progress"
)

public final class Setting implements Named {

    private final String name;
    private final SettingType type;

    private Object value;
    private Object defaultValue;

    public Setting(String name, SettingType type, Object defaultValue) {
        this.name = name;
        this.type = type;

        if (setDefaultValue(defaultValue))
            setValue(defaultValue);
        else
            throw new IllegalArgumentException();
    }

    @Override
    public String getName() {
        return name;
    }

    public SettingType getType() {
        return type;
    }

    // this bitch got so many shields it's insane
    public Object getValue() {

        Optional<SettingType> valueType = SettingType.getTypeFromObject(value);

        if (valueType.isPresent() && valueType.get().equals(getType()))
            return value;
        else if (resetValue())
            return value;
        else
            throw new IllegalStateException(); // shits fucked if you get this far
    }

    /*
    Change the value of this setting. Returns true if the type of
    the value passed matched the type of this setting, and the
    change was successful. Returns false if the types did not
    match, and the change failed.
     */
    public boolean setValue(Object value) {

        Optional<SettingType> valueType = SettingType.getTypeFromObject(value);

        if (valueType.isPresent() && valueType.get().equals(getType())) {
            this.value = value;
            return true;
        }
        return false;
    }

    // Helper method that directly assigns a new value to this setting,
    // bypassing the type-parsing in the regular method.
    private boolean setValueWithType(Object value, @NotNull SettingType type) {
        if (type.equals(getType())) {
            this.value = value;
            return true;
        }
        return false;
    }

    // Does the same thing as the above function, but takes a String instead.
    // Returns false if the string cannot be parsed.
    public boolean setValueFromString(final String valueString) {

        Optional<Boolean> optionalBoolean = Conversion.parseBoolean(valueString);
        Optional<Integer> optionalInteger = Conversion.parseInteger(valueString);
        Optional<Double> optionalDouble = Conversion.parseDouble(valueString);

        if (optionalBoolean.isPresent()) {
            return setValueWithType(optionalBoolean.get(), SettingType.BOOLEAN);
        }
        else if (optionalDouble.isPresent()) {
            return setValueWithType(optionalDouble.get(), SettingType.DOUBLE);
        }
        else if (optionalInteger.isPresent()) {
            return setValueWithType(optionalInteger.get(), SettingType.INTEGER);
        }
        else {
            return setValueWithType(valueString, SettingType.STRING);
        }
    }

    /*
    Resets the value of this setting to the default. But it also double-checks
    whether the value and the default value of this setting match in their
    type. If they do not, then no reset occurs, and this method returns false.
     */
    public boolean resetValue() {

        Optional<SettingType> valueType = SettingType.getTypeFromObject(value);
        Optional<SettingType> defaultType = SettingType.getTypeFromObject(defaultValue);

        if (valueType.isPresent() && defaultType.isPresent() && valueType.get() == defaultType.get()) {
            value = defaultValue;
            return true;
        }
        else
            return false;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    /*
    Private method for setting the default value, since I don't want
    to do the necessary checks inside the constructor. Like the regular
    value-setter, it compares the type of the passed value to the type
    of this setting.
     */
    private boolean setDefaultValue(Object value) {

        Optional<SettingType> valueType = SettingType.getTypeFromObject(value);

        if (valueType.isPresent() && valueType.get().equals(getType())) {
            this.defaultValue = value;
            return true;
        }
        else
            return false;
    }
}
