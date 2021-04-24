package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.gui.TextHudRenderer;
import me.cubert3d.palladium.gui.text.TextList;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.module.setting.list.ItemListSetting;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.function.Supplier;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/10/2021"
)

public final class SuppliesModule extends Module {

    public static final TextList suppliesList;

    public SuppliesModule() {
        super("Supplies", "Displays important supplies and their quantities you have on-screen.",
                ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
        this.addSetting(new ItemListSetting("Items"));
    }

    @Override
    protected void onEnable() {
        TextHudRenderer.getTextManager().setBottomRightList(suppliesList);
    }

    @Override
    protected void onDisable() {
        TextHudRenderer.getTextManager().clearBottomRightList();
    }

    static {
        suppliesList = new TextList(
                () -> new ColorText("Supplies"),
                () -> {
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
        );
    }
}
