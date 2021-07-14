package me.cubert3d.palladium.module.setting;

import me.cubert3d.palladium.module.setting.list.BlockListSetting;
import me.cubert3d.palladium.module.setting.list.EntityListSetting;
import me.cubert3d.palladium.module.setting.list.ItemListSetting;
import me.cubert3d.palladium.module.setting.list.ListSetting;
import me.cubert3d.palladium.module.setting.list.StringListSetting;
import me.cubert3d.palladium.module.setting.single.BooleanSetting;
import me.cubert3d.palladium.module.setting.single.DoubleSetting;
import me.cubert3d.palladium.module.setting.single.IntegerSetting;
import me.cubert3d.palladium.module.setting.single.SingleSetting;
import me.cubert3d.palladium.module.setting.single.StringSetting;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        description = "Module setting.",
        authors = "cubert3d",
        date = "4/7/2021",
        type = ClassType.SETTING
)

public abstract class Setting {

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

    public final SingleSetting<?> asSingleSetting() {
        if (!this.isListSetting())
            return (SingleSetting<?>) this;
        else
            throw new ClassCastException();
    }

    public final BooleanSetting asBooleanSetting() {
        if (this.getType().equals(SettingType.BOOLEAN))
            return (BooleanSetting) this;
        else
            throw new ClassCastException();
    }

    public final IntegerSetting asIntegerSetting() {
        if (this.getType().equals(SettingType.INTEGER))
            return (IntegerSetting) this;
        else
            throw new ClassCastException();
    }

    public final DoubleSetting asDoubleSetting() {
        if (this.getType().equals(SettingType.DOUBLE))
            return (DoubleSetting) this;
        else
            throw new ClassCastException();
    }

    public final StringSetting asStringSetting() {
        if (this.getType().equals(SettingType.STRING))
            return (StringSetting) this;
        else
            throw new ClassCastException();
    }

    public final ListSetting<?> asListSetting() {
        if (this.isListSetting())
            return (ListSetting<?>) this;
        else
            throw new ClassCastException();
    }

    public final StringListSetting asStringListSetting() {
        if (this.getType().equals(SettingType.LIST_STRING))
            return (StringListSetting) this;
        else
            throw new ClassCastException();
    }

    public final BlockListSetting asBlockListSetting() {
        if (this.getType().equals(SettingType.LIST_BLOCK))
            return (BlockListSetting) this;
        else
            throw new ClassCastException();
    }

    public final EntityListSetting asEntityListSetting() {
        if (this.getType().equals(SettingType.LIST_ENTITY))
            return (EntityListSetting) this;
        else
            throw new ClassCastException();
    }

    public final ItemListSetting asItemListSetting() {
        if (this.getType().equals(SettingType.LIST_ITEM))
            return (ItemListSetting) this;
        else
            throw new ClassCastException();
    }
}
