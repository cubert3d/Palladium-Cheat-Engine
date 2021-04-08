package me.cubert3d.palladium.module.setting.list;

import me.cubert3d.palladium.module.setting.SettingType;
import net.minecraft.block.Block;

import java.util.Collection;

public final class BlockListSetting extends ListSetting<Block> {

    public BlockListSetting(String name) {
        super(name);
    }

    public BlockListSetting(String name, Collection<Block> defaultCollection) {
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
        return SettingType.LIST_BLOCK;
    }

    @Override
    public final boolean addElement(Block element) {
        // No duplicates allowed.
        if (!getList().contains(element))
            return super.addElement(element);
        else
            return false;
    }

    @Override
    public final boolean removeElement(Block element) {
        return super.removeElement(element);
    }
}
