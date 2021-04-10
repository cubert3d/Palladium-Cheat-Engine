package me.cubert3d.palladium.module.setting;

import me.cubert3d.palladium.module.setting.single.*;
import me.cubert3d.palladium.module.setting.list.*;
import me.cubert3d.palladium.util.Named;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import me.cubert3d.palladium.util.annotation.InternalOnly;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/7/2021"
)

public abstract class BaseSetting implements Named {

    public static final String[] FORBIDDEN_SETTING_NAMES = new String[]{
            "enable",
            "disable",
            "toggle",
            "settings"
    };

    private final String name;

    protected BaseSetting(final String name) {
        this.name = name;
    }

    @Override
    public final String getName() {
        return name;
    }

    public abstract SettingType getType();

    public abstract boolean isListSetting();

    public abstract void reset();

    // CONVERSION

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
