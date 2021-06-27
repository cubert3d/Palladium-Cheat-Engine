package me.cubert3d.palladium.module.setting.list;

import me.cubert3d.palladium.module.setting.SettingType;
import me.cubert3d.palladium.util.Common;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.Collection;
import java.util.Optional;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/7/2021"
)

public final class EntityListSetting extends ListSetting<EntityType<? extends Entity>> {

    public EntityListSetting(String name) {
        super(name);
    }

    public EntityListSetting(String name, Collection<EntityType<? extends Entity>> defaultCollection) {
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
        return SettingType.LIST_ENTITY;
    }

    @Override
    public final boolean addElement(EntityType<? extends Entity> element) {
        // No duplicates allowed.
        if (!getList().contains(element))
            return super.addElement(element);
        else
            return false;
    }

    @Override
    public final boolean removeElement(EntityType<? extends Entity> element) {
        return super.removeElement(element);
    }

    @Override
    protected final String getElementAsString(EntityType<? extends Entity> element) {
        return EntityType.getId(element).toString();
    }

    @Override
    protected final Optional<EntityType<? extends Entity>> parseString(String string) {
        return Optional.of(Common.getEntityTypeFromString(string));
    }
}
