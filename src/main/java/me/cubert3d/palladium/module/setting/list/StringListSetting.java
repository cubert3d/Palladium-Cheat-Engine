package me.cubert3d.palladium.module.setting.list;

import me.cubert3d.palladium.module.setting.SettingType;
import me.cubert3d.palladium.module.setting.single.StringSetting;
import me.cubert3d.palladium.util.annotation.ClassDescription;

import java.util.Collection;
import java.util.Optional;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/7/2021"
)

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
        if (element.length() > StringSetting.MAX_STRING_LENGTH)
            element = element.trim().substring(0, StringSetting.MAX_STRING_LENGTH);
        return super.addElement(element);
    }

    @Override
    public final boolean removeElement(String element) {
        return super.removeElement(element);
    }

    @Override
    protected final String getElementAsString(String element) {
        return element;
    }

    @Override
    public final Optional<String> convertStringToElement(String string) {
        return Optional.of(string);
    }
}
