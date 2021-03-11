package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.event.callback.ItemStackDamageCallback;
import me.cubert3d.palladium.event.callback.MineBlockCallback;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.function.Consumer;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/8/2021",
        status = "in-progress"
)

@Mixin(ItemStack.class)
public final class ItemStackDamageMixin {

    /*
    @Inject(at = @At(value = "TAIL"),
            method = "damage(ILjava/util/Random;Lnet/minecraft/server/network/ServerPlayerEntity;)Z")
    private void onItemStackDamage(int amount, Random random, @Nullable ServerPlayerEntity player,
                                   CallbackInfoReturnable<Boolean> info) {
        ActionResult result = ItemStackDamageCallback.EVENT.invoker().interact(player, (ItemStack) (Object) this);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
     */

    /*
    @Inject(at = @At(value = "TAIL"),
            method = "damage(I" +
                    "Lnet/minecraft/entity/LivingEntity;" +
                    "Ljava/util/function/Consumer;)V")
    private void onItemStackDamage(int amount, LivingEntity entity, Consumer<?> breakCallback,
                                   CallbackInfo info) {
        ActionResult result = ItemStackDamageCallback.EVENT.invoker().interact((PlayerEntity) entity, (ItemStack) (Object) this);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
     */

    @Inject(at = @At(value = "TAIL"),
            method = "damage(ILjava/util/Random;Lnet/minecraft/server/network/ServerPlayerEntity;)Z")
    private void onItemStackDamage(int amount, Random random, @Nullable ServerPlayerEntity player,
                                   CallbackInfoReturnable<Boolean> info) {
        ActionResult result = ItemStackDamageCallback.EVENT.invoker().interact(player, (ItemStack) (Object) this);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
