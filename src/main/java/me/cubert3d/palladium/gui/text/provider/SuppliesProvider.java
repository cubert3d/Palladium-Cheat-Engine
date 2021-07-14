package me.cubert3d.palladium.gui.text.provider;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.module.modules.gui.SuppliesModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@ClassInfo(
        authors = "cubert3d",
        date = "4/23/2021",
        type = ClassType.PROVIDER
)

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

        for (Item item : Palladium.getInstance().getModuleManager().getModuleByClass(SuppliesModule.class).getSetting("Items").asItemListSetting().getList()) {
            int itemCount = 0;
            for (ItemStack stack : MinecraftClient.getInstance().player.inventory.main) {
                if (stack.getItem().equals(item))
                    itemCount += stack.getCount();
            }
            for (ItemStack stack : MinecraftClient.getInstance().player.inventory.armor) {
                if (stack.getItem().equals(item))
                    itemCount += stack.getCount();
            }
            for (ItemStack stack : MinecraftClient.getInstance().player.inventory.offHand) {
                if (stack.getItem().equals(item))
                    itemCount += stack.getCount();
            }
            text.add(new ColorText(item.getName().getString() + " x" + itemCount));
        }

        return text;
    }
}
