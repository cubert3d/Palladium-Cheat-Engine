package me.cubert3d.palladium.module.modules.player;

import me.cubert3d.palladium.event.callback.ToolSaverCallback;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ClassInfo(
        authors = "cubert3d",
        date = "3/7/2021",
        type = ClassType.MODULE,
        complete = false
)

public final class ToolSaverModule extends ToggleModule {

    private final ReplacementSorter sorter;

    public ToolSaverModule() {
        super("ToolSaver", "Swaps tools out of your hand when they reach their last point of durability.");
        this.sorter = new ReplacementSorter();
    }

    @Override
    public void onLoad() {

        ToolSaverCallback.EVENT.register(armorSwap -> {
            if (isEnabled()) {
                ClientPlayerEntity player = MinecraftClient.getInstance().player;
                if (player != null) {
                    PlayerInventory inventory = player.inventory;

                    // If this is not an armor swap, just check the item in the player's hand.
                    if (!armorSwap) {
                        ItemStack stack = inventory.getMainHandStack();
                        int durability = stack.getMaxDamage() - stack.getDamage();
                        if (stack.isDamageable() && durability == 1) {
                            swapTool(stack, player);
                        }
                    }
                    // If it is, check all of the player's armor (and shield).
                    else {
                        for (ItemStack armor : inventory.armor) {
                            int durability = armor.getMaxDamage() - armor.getDamage();
                            if (armor.isDamageable() && durability == 2) {
                                swapArmor(armor, player);
                            }
                        }
                    }
                }
            }
        });
    }

    private void swapTool(ItemStack stack, @NotNull ClientPlayerEntity player) {
        ClientPlayerInteractionManager interactionManager = MinecraftClient.getInstance().interactionManager;
        if (interactionManager != null) {

            PlayerInventory inventory = player.inventory;
            final int syncID = player.currentScreenHandler.syncId;
            final int slotID = convertIndex(getBestReplacementIndex(inventory, stack));
            final int clickData = inventory.getSlotWithStack(stack);
            final SlotActionType actionType = SlotActionType.SWAP;

            interactionManager.clickSlot(syncID, slotID, clickData, actionType, player);
        }
    }

    private void swapArmor(@NotNull ItemStack stack, @NotNull ClientPlayerEntity player) {
        ClientPlayerInteractionManager interactionManager = MinecraftClient.getInstance().interactionManager;
        if (interactionManager != null) {

            PlayerInventory inventory = player.inventory;
            final int syncID = player.currentScreenHandler.syncId;
            final SlotActionType actionType = SlotActionType.QUICK_MOVE;
            final int clickData = 0;

            int indexFrom = getArmorIndex(stack.getItem(), inventory);
            int indexTo = convertIndex(getBestArmorReplacementIndex(inventory, stack));

            // Only swap the armor if *can* be swapped.
            // if indexTo == -1, then the inventory has no empty room to put the almost broken armor into.
            if (indexTo >= 0) {
                interactionManager.clickSlot(syncID, indexFrom, clickData, actionType, player);
                interactionManager.clickSlot(syncID, indexTo, clickData, actionType, player);
            }
        }
    }

    private int getBestReplacementIndex(@NotNull PlayerInventory inventory, ItemStack stack) {

        List<ReplacementProfile> replacements = new ArrayList<>();

        for (int i = 0; i < inventory.main.size(); i++) {
            ItemStack currentStack = inventory.getStack(i);
            ReplacementType type = getReplacementType(stack, currentStack);
            if (type.isSuitable()) {
                replacements.add(new ReplacementProfile(currentStack, type, i));
            }
        }
        replacements.sort(sorter);

        if (replacements.size() > 0) {
            return replacements.get(0).getIndex();
        }
        else {
            return -1;
        }
    }

    private int getBestArmorReplacementIndex(@NotNull PlayerInventory inventory, ItemStack stack) {

        List<ReplacementProfile> replacements = new ArrayList<>();

        for (int i = 0; i < inventory.main.size(); i++) {
            ItemStack currentStack = inventory.getStack(i);
            ReplacementType type = getReplacementType(stack, currentStack);
            if (type.equals(ReplacementType.SAME) || type.equals(ReplacementType.EMPTY)) {
                replacements.add(new ReplacementProfile(currentStack, type, i));
            }
        }
        replacements.sort(sorter);

        if (replacements.size() > 0) {
            return replacements.get(0).getIndex();
        }
        else {
            return -1;
        }
    }

    private boolean isSameEquipmentType(@NotNull Item item1, @NotNull Item item2) {
        if (item1.getClass().equals(item2.getClass())) {
            if (item1 instanceof ArmorItem && item2 instanceof ArmorItem) {
                return ((ArmorItem) item1).getSlotType().equals(((ArmorItem) item2).getSlotType());
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    private ReplacementType getReplacementType(ItemStack stack, @NotNull ItemStack replacer) {
        // Check if the replacer item is equipment of the same type.
        if (replacer.isDamageable() && (replacer.getMaxDamage() - replacer.getDamage()) > 1
                && isSameEquipmentType(stack.getItem(), replacer.getItem())) {
            return ReplacementType.SAME;
        }

        if (!stack.isDamageable() && !stack.isEmpty()) {
            return ReplacementType.NON_DAMAGEABLE;
        }
        else if (stack.isEmpty()) {
            return ReplacementType.EMPTY;
        }
        else {
            return ReplacementType.NONE;
        }
    }

    private int getArmorIndex(Item armor, PlayerInventory inventory) {
        if (armor instanceof ArmorItem) {
            switch (((ArmorItem) armor).getSlotType()) {
                case HEAD: return 5;
                case CHEST: return 6;
                case LEGS: return 7;
                case FEET: return 8;
                case OFFHAND: return 45;
                case MAINHAND: return inventory.selectedSlot + 36;
                default: return -1;
            }
        }
        else {
            return -1;
        }
    }

    // Converts the inventory index from PlayerInventory indexing to ClickSlotC2SPacket indexing
    private int convertIndex(int index) {
        /*
        The inventory class and the click-slot packet use two different indices for their contents.
        The inventory class only indexes the the main inventory itself, along with the armor slots
        and off-hand slot; whereas the packet also indexes the crafting menu, because it also has
        to handle slots being clicked there as well, and not just in the main inventory.
         */
        int newIndex = -1;

        // Hotbar
        if (index >= 0 && index <= 8) {
            // In PI, the hotbar comes first; but in CSP, it comes after
            // everything else (except for the off-hand).
            newIndex = index + 36;
        }
        // Main
        else if (index >= 9 && index <= 35) {
            // Here the PI index corresponds to the CSP index.
            newIndex = index;
        }
        else if (index >= 36 && index <= 39) {
            // Helmet       = 5 = 8 - (39 - 36)
            // Chestplate   = 6 = 8 - (39 - 37)
            // Leggings     = 7 = 8 - (39 - 38)
            // Boots        = 8 = 8 - (39 - 39)
            newIndex = 8 - (index - 36);
        }
        else if (index == 40) {
            newIndex = 45;
        }

        return  newIndex;
    }

    enum ReplacementType {
        SAME(true),             // The replacement is the same kind of equipment.
        NON_DAMAGEABLE(true),   // The replacement is a non-damageable item.
        EMPTY(true),            // The replacement is an empty slot.
        NONE(false);            // Not a suitable replacement.

        private final boolean suitable;

        ReplacementType(boolean suitable) {
            this.suitable = suitable;
        }

        public boolean isSuitable() {
            return suitable;
        }
    }

    private static class ReplacementSorter implements Comparator<ReplacementProfile> {

        @Override
        public int compare(@NotNull ReplacementProfile o1, @NotNull ReplacementProfile o2) {

            ReplacementType type1 = o1.getType();
            ReplacementType type2 = o2.getType();

            if (type1.equals(type2)) {
                return 0;
            }
            else if (type1.equals(ReplacementType.SAME)) {
                return -1;
            }
            else if (type1.equals(ReplacementType.EMPTY)) {
                return 1;
            }
            return 0;
        }
    }

    private static class ReplacementProfile {
        private final ItemStack stack;
        private final ReplacementType type;
        private final int index;

        private ReplacementProfile(ItemStack stack, ReplacementType type, int index) {
            this.stack = stack;
            this.index = index;
            this.type = type;
        }

        public ItemStack getStack() {
            return stack;
        }

        public ReplacementType getType() {
            return type;
        }

        public int getIndex() {
            return index;
        }
    }
}
