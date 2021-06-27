package me.cubert3d.palladium.module.setting.list;

import me.cubert3d.palladium.Configuration;
import me.cubert3d.palladium.module.setting.Setting;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/7/2021"
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

    protected boolean addElement(E element) {
        return list.add(element);
    }

    protected boolean removeElement(E element) {
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
    public final void setFromString(@NotNull String string) throws IOException {

        List<E> list = new ArrayList<>();
        String[] strings = string.split(Configuration.LIST_DELIMITER);

        // Iterate through the split strings, and parse each of them.
        for (String splitString : strings) {
            Optional<E> optional = parseString(splitString);
            if (optional.isPresent()) {
                list.add(optional.get());
            }
            // If the string does not successfully parse, then throw an exception, and skip it.
            else {
                throw new IOException();
            }
        }

        setList(list);
    }

    protected abstract Optional<E> parseString(String string);
}
