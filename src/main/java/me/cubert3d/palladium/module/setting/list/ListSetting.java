package me.cubert3d.palladium.module.setting.list;

import me.cubert3d.palladium.module.setting.BaseSetting;
import me.cubert3d.palladium.util.annotation.ClassDescription;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/7/2021"
)

public abstract class ListSetting<T> extends BaseSetting {

    // The maximum number of list elements that can be displayed.
    public static final int MAX_DISPLAY_COUNT = 50;

    private final List<T> list = new ArrayList<>();
    private final List<T> defaultList = new ArrayList<>();
    private final boolean useDefaultList;

    protected ListSetting(final String name) {
        super(name);
        useDefaultList = false;
    }

    protected ListSetting(final String name, Collection<T> defaultCollection) {
        super(name);
        list.addAll(defaultCollection);
        defaultList.addAll(defaultCollection);
        useDefaultList = true;
    }

    @Override
    public final boolean isListSetting() {
        return true;
    }

    public final List<T> getList() {
        return list;
    }

    public final List<T> getDefaultList() {
        return defaultList;
    }

    @Override
    public final void reset() {
        list.clear();
        if (useDefaultList)
            list.addAll(defaultList);
    }

    protected boolean addElement(T element) {
        return list.add(element);
    }

    protected boolean removeElement(T element) {
        return list.remove(element);
    }
}
