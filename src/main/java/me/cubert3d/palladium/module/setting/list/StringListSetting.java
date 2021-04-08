package me.cubert3d.palladium.module.setting.list;

import me.cubert3d.palladium.module.setting.SettingType;
import me.cubert3d.palladium.module.setting.single.StringSetting;

import java.util.Collection;

public final class StringListSetting extends ListSetting<String> {

    public StringListSetting(String name) {
        super(name);
    }

    public StringListSetting(String name, Collection<String> defaultCollection) {
        super(name, defaultCollection);
    }

    @Override
    public final String toString() {
        String string = "";
        for (int i = 0; i < getList().size() && i < MAX_DISPLAY_COUNT; i++) {
            string = string.concat(getList().get(i));
            if (i < getList().size() - 1 && i < MAX_DISPLAY_COUNT - 1)
                string = string.concat(", ");
        }
        return string;
    }

    @Override
    public final SettingType getType() {
        return SettingType.LIST_STRING;
    }

    @Override
    public final boolean addElement(String element) {
        element = element.trim().substring(0, StringSetting.MAX_STRING_LENGTH);
        return super.addElement(element);
    }

    @Override
    public final boolean removeElement(String element) {
        return super.removeElement(element);
    }
}
