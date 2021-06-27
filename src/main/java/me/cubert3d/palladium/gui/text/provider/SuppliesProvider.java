package me.cubert3d.palladium.gui.text.provider;

import me.cubert3d.palladium.util.Common;
import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.modules.gui.SuppliesModule;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class SuppliesProvider extends TextProvider {

    public SuppliesProvider() {

    }

    @Override
    public @NotNull ColorText getHeader() {
        return new ColorText("Supplies");
    }

    @Override
    public ArrayList<ColorText> getBody() {
        ArrayList<ColorText> text = new ArrayList<>();

        for (Item item : ModuleManager.getModuleByClass(SuppliesModule.class).getSetting("Items").asItemListSetting().getList()) {
            int itemCount = 0;
            for (ItemStack stack : Common.getPlayer().inventory.main) {
                if (stack.getItem().equals(item))
                    itemCount += stack.getCount();
            }
            for (ItemStack stack : Common.getPlayer().inventory.armor) {
                if (stack.getItem().equals(item))
                    itemCount += stack.getCount();
            }
            for (ItemStack stack : Common.getPlayer().inventory.offHand) {
                if (stack.getItem().equals(item))
                    itemCount += stack.getCount();
            }
            text.add(new ColorText(item.getName().getString() + " x" + itemCount));
        }

        return text;
    }
}
