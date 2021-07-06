package me.cubert3d.palladium.module.setting;

import me.cubert3d.palladium.module.setting.single.*;
import me.cubert3d.palladium.module.setting.list.*;
import me.cubert3d.palladium.util.annotation.Named;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import me.cubert3d.palladium.util.annotation.InternalOnly;

import java.io.IOException;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/7/2021"
)

public abstract class Setting implements Named {

    public static final String[] FORBIDDEN_SETTING_NAMES = new String[]{
            "enable",
            "disable",
            "toggle",
            "settings"
    };

    private final String name;

    protected Setting(final String name) {
        this.name = name;
    }

    @Override
    public final String getName() {
        return name;
    }

    public boolean isSet() {
        return true;
    }

    public abstract SettingType getType();

    public abstract boolean isListSetting();

    public abstract void reset();

    /*
    Below are methods for reading and writing this setting's value, both for
    player input, and for getting setting values from the config file.
    The "getAsString" method simply returns the value of this setting as a
    string, so that it can be written to a config file. The "setFromString"
    method takes a string--either from the in-game command line, or from a
    config file--and converts it into a value of the same type as this setting,
    and then sets the value of this setting to that value. It also uses a helper
    method, "parseString", which attempts to parse the string; it returns an
    optional, which contains the value if it could be parsed, or is empty, if
    it could not.
     */

    public abstract String getAsString();

    public abstract void setFromString(String string);

    // CONVERSION

    @InternalOnly
    public final SingleSetting<?> asSingleSetting() {
        if (!this.isListSetting())
            return (SingleSetting<?>) this;
        else
            throw new ClassCastException();
    }

    @InternalOnly
    public final BooleanSetting asBooleanSetting() {
        if (this.getType().equals(SettingType.BOOLEAN))
            return (BooleanSetting) this;
        else
            throw new ClassCastException();
    }

    @InternalOnly
    public final IntegerSetting asIntegerSetting() {
        if (this.getType().equals(SettingType.INTEGER))
            return (IntegerSetting) this;
        else
            throw new ClassCastException();
    }

    @InternalOnly
    public final DoubleSetting asDoubleSetting() {
        if (this.getType().equals(SettingType.DOUBLE))
            return (DoubleSetting) this;
        else
            throw new ClassCastException();
    }

    @InternalOnly
    public final StringSetting asStringSetting() {
        if (this.getType().equals(SettingType.STRING))
            return (StringSetting) this;
        else
            throw new ClassCastException();
    }

    @InternalOnly
    public final ListSetting<?> asListSetting() {
        if (this.isListSetting())
            return (ListSetting<?>) this;
        else
            throw new ClassCastException();
    }

    @InternalOnly
    public final StringListSetting asStringListSetting() {
        if (this.getType().equals(SettingType.LIST_STRING))
            return (StringListSetting) this;
        else
            throw new ClassCastException();
    }

    @InternalOnly
    public final BlockListSetting asBlockListSetting() {
        if (this.getType().equals(SettingType.LIST_BLOCK))
            return (BlockListSetting) this;
        else
            throw new ClassCastException();
    }

    @InternalOnly
    public final EntityListSetting asEntityListSetting() {
        if (this.getType().equals(SettingType.LIST_ENTITY))
            return (EntityListSetting) this;
        else
            throw new ClassCastException();
    }

    @InternalOnly
    public final ItemListSetting asItemListSetting() {
        if (this.getType().equals(SettingType.LIST_ITEM))
            return (ItemListSetting) this;
        else
            throw new ClassCastException();
    }
}
