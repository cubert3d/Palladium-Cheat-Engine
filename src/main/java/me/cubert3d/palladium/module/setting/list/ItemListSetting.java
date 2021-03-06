package me.cubert3d.palladium.module.setting.list;

import me.cubert3d.palladium.module.setting.SettingType;
import me.cubert3d.palladium.util.IdentifierUtil;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.Collection;
import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "4/7/2021",
        type = ClassType.SETTING
)

public final class ItemListSetting extends ListSetting<Item> {

    public ItemListSetting(final String name, final String description) {
        super(name, description);
    }

    public ItemListSetting(final String name, final String description, Collection<Item> defaultCollection) {
        super(name, description, defaultCollection);
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
        return Optional.of(IdentifierUtil.getItemFromString(string));
    }
}
