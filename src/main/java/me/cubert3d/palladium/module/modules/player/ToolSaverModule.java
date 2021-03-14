package me.cubert3d.palladium.module.modules.player;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.event.callback.ItemStackDamageCallback;
import me.cubert3d.palladium.event.callback.MineBlockCallback;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/7/2021",
        status = "benched"
)

public final class ToolSaverModule extends Module {

    public ToolSaverModule() {
        super("ToolSaver", "Swaps tools out of your hand when they reach their last point of durability.",
                ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }

    @Override
    protected void onLoad() {

        MineBlockCallback.EVENT.register((player, stack) -> {

            if (!this.isEnabled())
                return ActionResult.PASS;

            System.out.println("Mined Block!");

            int damage = stack.getDamage();
            int maxDamage = stack.getMaxDamage();


            if (damage == (maxDamage - 1)) {
                swapTool(player, stack);
            }

            return ActionResult.PASS;
        });

        ItemStackDamageCallback.EVENT.register((player, stack) -> {

            if (!this.isEnabled())
                return ActionResult.PASS;

            int newDamage = stack.getDamage();
            Common.sendMessage(String.format("Damage: %d/%d", newDamage, stack.getMaxDamage()));
            System.out.println(String.format("Damage: %d/%d", newDamage, stack.getMaxDamage()));

            return ActionResult.PASS;
        });
    }

    private void swapTool(@NotNull PlayerEntity player, ItemStack stack) {

        PlayerInventory inventory = player.inventory;
        Pair<Integer, ItemStack> bestSwap = getBestSwap(inventory, stack);

        int indexFrom = inventory.getSlotWithStack(stack);
        int indexTo = bestSwap.getLeft();
        ItemStack swapWith = bestSwap.getRight();

        if (!player.world.isClient()) {

            System.out.println("Case 1");

            /*
                ClickSlotC2SPacket fields:

                syncID: used for syncing packets.
                clickData: for swapping, it is the number key that was pressed
                actionType:
                actionID: increments with each packet of this kind sent.

                slot:   the index of the slot that is clicked;
                        when an item is swapped by number key, it doesn't matter
                        if its swapped into or out of that slot, for it still
                        gives the index of that slot, rather than the index of
                        the hotbar slot it is swapped into or out of.

                stack:  this only actually represents the item-stack when that
                        information needs to be stored.
             */

            // The click-data, in this context, is basically the number key
            // that was pressed in the item swap.
            int clickData = indexFrom;

            int slot = convertIndex(indexTo);

            ClickSlotC2SPacket packet = new ClickSlotC2SPacket(0, slot, clickData, SlotActionType.SWAP, ItemStack.EMPTY, (short) 0);

            Objects.requireNonNull(Common.getMC().getNetworkHandler()).sendPacket(packet);

            inventory.updateItems();

            Common.sendMessage("Tool Saved!");
            System.out.println("Tool Saved!");
        }
        else {
            System.out.println("Case 2");

            inventory.setStack(indexTo, stack);
            inventory.setStack(indexFrom, swapWith);
        }
    }

    @Contract("_, _ -> new")
    private @NotNull Pair<Integer, ItemStack> getBestSwap(@NotNull PlayerInventory inventory, ItemStack stack) {

        int index = -1;
        ItemStack swapWith = ItemStack.EMPTY;

        for (int i = 0; i < inventory.main.size(); i++) {
            ItemStack currentStack = inventory.getStack(i);

            if (currentStack.isEmpty()
                    || !currentStack.isDamageable()
                    || currentStack.getDamage() < (currentStack.getMaxDamage() - 1)) {
                index = i;
                swapWith = currentStack;
                break;
            }
        }

        return new Pair<>(index, swapWith);
    }

    // Converts the inventory index from PlayerInventory indexing to ClickSlotC2SPacket indexing
    private static int convertIndex(int index) {
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
}
