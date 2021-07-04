package me.cubert3d.palladium.module.setting.list;

import me.cubert3d.palladium.module.setting.SettingType;
import me.cubert3d.palladium.util.Common;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.Collection;
import java.util.Optional;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/7/2021"
)

public final class ItemListSetting extends ListSetting<Item> {

    public ItemListSetting(String name) {
        super(name);
    }

    public ItemListSetting(String name, Collection<Item> defaultCollection) {
        super(name, defaultCollection);
    }

    @Override
    public final String toString() {
        String string = "";
        for (int i = 0; i < getList().size() && i < MAX_DISPLAY_COUNT; i++) {
            string = string.concat(getList().get(i).getName().getString());
            if (i < getList().size() - 1 && i < MAX_DISPLAY_COUNT - 1)
                string = string.concat(", ");
        }
        return string;
    }

    @Override
    public final SettingType getType() {
        return SettingType.LIST_ITEM;
    }

    @Override
    public final boolean addElement(Item element) {
        // No duplicates allowed.
        if (!getList().contains(element))
            return super.addElement(element);
        else
            return false;
    }

    @Override
    public final boolean removeElement(Item element) {
        return super.removeElement(element);
    }

    @Override
    protected final String getElementAsString(Item element) {
        return Registry.ITEM.getId(element).toString();
    }

    @Override
    public final Optional<Item> convertStringToElement(String string) {
        return Optional.of(Common.getItemFromString(string));
    }
}
