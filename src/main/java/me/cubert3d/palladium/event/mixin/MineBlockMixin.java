package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.event.callback.MineBlockCallback;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import me.cubert3d.palladium.util.annotation.DebugOnly;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/7/2021",
        status = "in-progress"
)

@DebugOnly
@Mixin(MiningToolItem.class)
public final class MineBlockMixin {

    /*
    @Inject(at = @At(value = "TAIL"),
            method = "postMine(Lnet/minecraft/item/ItemStack;" +    // ItemStack stack
                    "Lnet/minecraft/world/World;" +                 // World world
                    "Lnet/minecraft/block/BlockState;" +            // BlockState state
                    "Lnet/minecraft/util/math/BlockPos;" +          // BlockPos pos
                    "Lnet/minecraft/entity/LivingEntity;)Z")        // LivingEntity miner
    private void onMineBlock(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner,
                             CallbackInfoReturnable<Boolean> info) {
        ActionResult result = MineBlockCallback.EVENT.invoker().interact((PlayerEntity) miner, stack);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
     */

    /*
    @Inject(at = @At(value = "TAIL"),
            method = "postMine(Lnet/minecraft/world/World;" +       // World world
                    "Lnet/minecraft/block/BlockState;" +            // BlockState state
                    "Lnet/minecraft/util/math/BlockPos;" +          // BlockPos pos
                    "Lnet/minecraft/entity/player/PlayerEntity;)V") // LivingEntity miner
    private void onMineBlock(World world, BlockState state, BlockPos pos, PlayerEntity miner,
                             CallbackInfo info) {
        ActionResult result = MineBlockCallback.EVENT.invoker().interact(miner, (ItemStack) (Object) this);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
     */

    @Inject(at = @At(value = "TAIL"),
            method = "postMine(Lnet/minecraft/item/ItemStack;" +    // ItemStack stack
                    "Lnet/minecraft/world/World;" +                 // World world
                    "Lnet/minecraft/block/BlockState;" +            // BlockState state
                    "Lnet/minecraft/util/math/BlockPos;" +          // BlockPos pos
                    "Lnet/minecraft/entity/LivingEntity;)Z")        // LivingEntity miner
    private void onMineBlock(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner,
                             CallbackInfoReturnable<Boolean> info) {
        ActionResult result = MineBlockCallback.EVENT.invoker().interact((PlayerEntity) miner, stack);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
