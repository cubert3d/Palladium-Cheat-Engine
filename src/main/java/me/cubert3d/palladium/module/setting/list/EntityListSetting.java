package me.cubert3d.palladium.module.setting.list;

import me.cubert3d.palladium.module.setting.SettingType;
import me.cubert3d.palladium.util.IdentifierUtil;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.Collection;
import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "4/7/2021",
        type = ClassType.SETTING
)

public final class EntityListSetting extends ListSetting<EntityType<? extends Entity>> {

    public EntityListSetting(final String name, final String description) {
        super(name, description);
    }

    public EntityListSetting(final String name, final String description, Collection<EntityType<? extends Entity>> defaultCollection) {
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
    public final Optional<EntityType<? extends Entity>> convertStringToElement(String string) {
        return Optional.of(IdentifierUtil.getEntityTypeFromString(string));
    }
}
