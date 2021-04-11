package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.gui.HudRenderer;
import me.cubert3d.palladium.gui.HudTextManager;
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
        date = "4/10/2021",
        status = "complete"
)

public final class SuppliesModule extends Module {

    private static final Supplier<ArrayList<String>> suppliesSupplier;

    public SuppliesModule() {
        super("Supplies", "Displays important supplies and their quantities you have on-screen.",
                ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
        this.addSetting(new ItemListSetting("Items"));
    }

    @Override
    protected void onEnable() {
        HudRenderer.getTextManager().setBottomRightSupplier(suppliesSupplier);
    }

    @Override
    protected void onDisable() {
        HudRenderer.getTextManager().clearBottomRightSupplier();
    }

    static {
        suppliesSupplier = () -> {
            ArrayList<String> strings = new ArrayList<>();

            for (Item item : ModuleManager.getModuleByClass(SuppliesModule.class).getSetting("Items").asItemListSetting().getList()) {
                int itemCount = 0;
                for (ItemStack stack : Common.getClientPlayer().inventory.main) {
                    if (stack.getItem().equals(item))
                        itemCount += stack.getCount();
                }
                for (ItemStack stack : Common.getClientPlayer().inventory.armor) {
                    if (stack.getItem().equals(item))
                        itemCount += stack.getCount();
                }
                for (ItemStack stack : Common.getClientPlayer().inventory.offHand) {
                    if (stack.getItem().equals(item))
                        itemCount += stack.getCount();
                }
                strings.add(item.getName().getString() + " x" + itemCount);
            }

            return strings;
        };
    }
}
