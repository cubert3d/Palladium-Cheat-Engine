package me.cubert3d.palladium.module.setting.list;

import me.cubert3d.palladium.Configuration;
import me.cubert3d.palladium.module.setting.Setting;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.exception.SettingParseException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "4/7/2021",
        type = ClassType.SETTING
)

public abstract class ListSetting<E> extends Setting {

    // The maximum number of list elements that can be displayed.
    public static final int MAX_DISPLAY_COUNT = 50;

    private final List<E> list = new ArrayList<>();
    private final List<E> defaultList = new ArrayList<>();
    private final boolean useDefaultList;

    protected ListSetting(final String name) {
        super(name);
        useDefaultList = false;
    }

    protected ListSetting(final String name, Collection<E> defaultCollection) {
        super(name);
        list.addAll(defaultCollection);
        defaultList.addAll(defaultCollection);
        useDefaultList = true;
    }

    @Override
    public final boolean isListSetting() {
        return true;
    }

    public final List<E> getList() {
        return list;
    }

    public final List<E> getDefaultList() {
        return defaultList;
    }

    @Override
    public final void reset() {
        list.clear();
        if (useDefaultList)
            list.addAll(defaultList);
    }

    public boolean addElement(E element) {
        return list.add(element);
    }

    public boolean removeElement(E element) {
        return list.remove(element);
    }

    private void setList(List<E> list) {
        this.list.clear();
        this.list.addAll(list);
    }

    @Override
    public final String getAsString() {

        String string = "";
        int counter = 0;

        for (E element : getList()) {

            String append = getElementAsString(element);
            string = string.concat(append.trim());

            if (counter < getList().size() - 1) {
                string = string.concat(Configuration.LIST_DELIMITER);
            }

            counter++;
        }

        return string;
    }

    protected abstract String getElementAsString(E element);

    @Override
    public final void setFromString(@NotNull String string) {

        /*
        For list-type settings, this method is specifically used for the config file reading.
        It is much easier get the string and send it as a whole to this method to be split up
        and parsed, than it is to check if the setting is a list and then split up the string.
         */

        List<E> list = new ArrayList<>();
        String[] strings = string.split(Configuration.LIST_DELIMITER);

        // Iterate through the split strings, and parse each of them.
        for (String splitString : strings) {
            Optional<E> optional = convertStringToElement(splitString);
            if (optional.isPresent()) {
                list.add(optional.get());
            }
            // If the string does not successfully parse, then throw an exception, and skip it.
            else {
                throw new SettingParseException();
            }
        }

        setList(list);
    }

    public abstract Optional<E> convertStringToElement(String string);
}
