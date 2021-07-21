package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.gui.text.TextProvider;
import me.cubert3d.palladium.gui.widget.window.DisplayWindow;
import me.cubert3d.palladium.module.setting.list.ItemListSetting;
import me.cubert3d.palladium.module.setting.single.BooleanSetting;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@ClassInfo(
        authors = "cubert3d",
        date = "4/10/2021",
        type = ClassType.MODULE
)

public final class SuppliesModule extends AbstractHudModule {

    private final TextProvider suppliesList;
    private final ItemListSetting itemsSetting;
    private final BooleanSetting rawCountsSetting;
    private final BooleanSetting hideEmptySetting;

    public SuppliesModule() {
        super("Supplies", "Displays important supplies and their quantities you have on-screen.");
        this.suppliesList = new SuppliesProvider(this);
        this.itemsSetting = new ItemListSetting("Items", "The items whose counts are to be displayed.");
        this.rawCountsSetting = new BooleanSetting("RawCounts", "Whether to show raw counts, or number of stacks and remainder.", false);
        this.hideEmptySetting = new BooleanSetting("HideEmpty", "Whether or not to hide the supplies of which you have none.", false);
        this.addSetting(itemsSetting);
        this.addSetting(rawCountsSetting);
        this.addSetting(hideEmptySetting);
    }

    @Override
    protected final void onEnable() {
        super.onEnable();
        getTextManager().setBottomRightList(suppliesList);
    }

    @Override
    protected final void onDisable() {
        super.onDisable();
        getTextManager().clearBottomRightList();
    }

    @Override
    protected final @NotNull DisplayWindow createWindow() {
        DisplayWindow newWindow = new DisplayWindow("supplies", suppliesList);
        newWindow.setX(25);
        newWindow.setY(25);
        newWindow.setWidth(150);
        newWindow.setHeight(91);
        newWindow.setColor(Colors.BACKGROUND_LAVENDER);
        return newWindow;
    }

    private List<Item> getItems() {
        return itemsSetting.getList();
    }

    private boolean shouldShowRawCounts() {
        return rawCountsSetting.getValue();
    }

    private boolean shouldHideEmpty() {
        return hideEmptySetting.getValue();
    }

    private static class SuppliesProvider extends TextProvider {

        private final SuppliesModule suppliesModule;

        private SuppliesProvider(SuppliesModule suppliesModule) {
            super();
            this.suppliesModule = suppliesModule;
        }

        @Override
        public ColorText getTitle() {
            return new ColorText("Supplies");
        }

        @Override
        public ArrayList<ColorText> getBody() {
            ArrayList<ColorText> text = new ArrayList<>();
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            List<Item> items = suppliesModule.getItems();
            boolean showRawCounts = suppliesModule.shouldShowRawCounts();
            boolean hideEmpty = suppliesModule.shouldHideEmpty();

            if (player != null) {
                for (Item item : items) {
                    String itemText;
                    int itemCount = 0;
                    for (ItemStack stack : player.inventory.main) {
                        if (stack.getItem().equals(item))
                            itemCount += stack.getCount();
                    }
                    for (ItemStack stack : player.inventory.armor) {
                        if (stack.getItem().equals(item))
                            itemCount += stack.getCount();
                    }
                    for (ItemStack stack : player.inventory.offHand) {
                        if (stack.getItem().equals(item))
                            itemCount += stack.getCount();
                    }

                    if (hideEmpty && itemCount == 0) {
                        continue;
                    }

                    if (showRawCounts) {
                        itemText = item.getName().getString() + " x" + itemCount;
                    }
                    else {
                        int itemMaxCount = item.getMaxCount();
                        int numFullStacks = itemCount / itemMaxCount;
                        int remainder = itemCount % itemMaxCount;
                        itemText = String.format("%s %dx%d + %d", item.getName().getString(), numFullStacks, itemMaxCount, remainder);
                    }
                    text.add(new ColorText(itemText));
                }
            }

            return text;
        }
    }
}
