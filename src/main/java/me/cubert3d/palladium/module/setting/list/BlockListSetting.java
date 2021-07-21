package me.cubert3d.palladium.module.setting.list;

import me.cubert3d.palladium.module.setting.SettingType;
import me.cubert3d.palladium.util.IdentifierUtil;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

import java.util.Collection;
import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "4/7/2021",
        type = ClassType.SETTING
)

public final class BlockListSetting extends ListSetting<Block> {

    public BlockListSetting(final String name, final String description) {
        super(name, description);
    }

    public BlockListSetting(final String name, final String description, Collection<Block> defaultCollection) {
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

    @Override
    protected String getElementAsString(Block element) {
        return Registry.BLOCK.getId(element).toString();
    }

    @Override
    public Optional<Block> convertStringToElement(String string) {
        return Optional.of(IdentifierUtil.getBlockFromString(string));
    }
}
