package me.cubert3d.palladium.module.modules.player;

import me.cubert3d.palladium.util.Common;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.world.GameMode;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/6/2021",
        status = "complete"
)

public final class AutoToolModule extends Module {

    // TODO: add setting for whether to pick a tool based on its effectiveness, or its durability.

    public AutoToolModule() {
        super("AutoTool", "Switches to the best tool in your hotbar when you try to mine a block.",
                ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
    }

    @Override
    protected void onLoad() {
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {

            // The player must not be in creative or spectator mode.
            if (!this.isEnabled()
                    || player.isCreative()
                    || player.isSpectator())
                return ActionResult.PASS;

            boolean inAdventureMode = Common.getGameMode() == GameMode.ADVENTURE;

            int bestToolIndex = getBestToolIndex(player, world.getBlockState(pos), inAdventureMode);

            if (bestToolIndex >= 0 && bestToolIndex < 9)
                player.inventory.selectedSlot = bestToolIndex;

            return ActionResult.PASS;
        });
    }

    private int getBestToolIndex(PlayerEntity player, BlockState blockState, boolean isInAdventureMode) {

        // TODO: make a special case for Adventure mode

        /*
         For each item examined in the loop, if its mining speed
         is higher than that of any item previously examined, then
         this variable is updated to equal the mining speed of this
         item; after which, the mining speed of subsequent items will
         be checked against this new best-mining-speed.
        */
        float lastBestMiningSpeed = 1.0F;

        int lastBestDamage = 0;
        /*
         The index of the item with the best mining speed. Defaults
         to -1 if no item with an effective mining speed is found.
        */
        int indexThereof = -1;

        for (int i = 0; i < PlayerInventory.getHotbarSize(); i++) {

            ItemStack itemStack = player.inventory.getStack(i);

            // The item and its mining speed.
            Item item = itemStack.getItem();
            float miningSpeed = item.getMiningSpeedMultiplier(itemStack, blockState);

            /*
            Check whether this item is more effective than anything previously
            examined in this method so far. If it is, update the last-best-mining-
            speed that the following items will be checked against, and update
            the index that will be returned.
             */
            if (miningSpeed > lastBestMiningSpeed) {
                lastBestMiningSpeed = miningSpeed;
                lastBestDamage = itemStack.getDamage();
                indexThereof = i;
            }
            /*
             If the mining speed is equal to the last-best-mining-speed (but
             is also above the baseline 1.0F--it is a tool still), then check
             whether the tool's durability is less than the last best tool.
            */
            else if (miningSpeed == lastBestMiningSpeed
                    && miningSpeed > 1.0F) {
                if (itemStack.getDamage() > lastBestDamage) {
                    lastBestMiningSpeed = miningSpeed;
                    lastBestDamage = itemStack.getDamage();
                    indexThereof = i;
                }
            }
        }
        return indexThereof;
    }
}
