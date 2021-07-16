package me.cubert3d.palladium.module.modules.command;

import me.cubert3d.palladium.module.modules.CommandModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import org.jetbrains.annotations.NotNull;

@ClassInfo(
        authors = "cubert3d",
        date = "7/15/2021",
        type = ClassType.MODULE
)

public final class DropCommand extends CommandModule {

    public DropCommand() {
        super("Drop", "Drops the held item.");
    }

    @Override
    protected final void execute(String[] args) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();

        if (player != null && networkHandler != null) {
            if (args.length > 0) {
                switch (args[0]) {
                    case "single":
                        dropStack(player, networkHandler, false);
                        break;
                    case "stack":
                        dropStack(player, networkHandler, true);
                        break;
                }
            }
            else {
                dropStack(player, networkHandler, false);
            }
        }
    }

    private void dropStack(@NotNull ClientPlayerEntity player, @NotNull ClientPlayNetworkHandler networkHandler, boolean all) {
        int syncID = player.currentScreenHandler.syncId;
        int index = player.inventory.selectedSlot + 36;
        int clickData = all ? 1 : 0;
        SlotActionType actionType = SlotActionType.THROW;
        ItemStack stack = player.inventory.getMainHandStack();
        short actionID = player.currentScreenHandler.getNextActionId(player.inventory);
        networkHandler.sendPacket(new ClickSlotC2SPacket(syncID, index, clickData, actionType, stack, actionID));
    }
}
