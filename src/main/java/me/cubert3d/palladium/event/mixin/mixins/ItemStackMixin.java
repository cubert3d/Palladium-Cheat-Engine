package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.callback.EnchantmentTooltipsCallback;
import me.cubert3d.palladium.event.callback.TooltipsCallback;
import me.cubert3d.palladium.event.mixin.MixinCaster;
import me.cubert3d.palladium.module.modules.render.TooltipsModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "3/8/2021",
        type = ClassType.MIXIN
)

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements MixinCaster<ItemStack> {

    @Inject(method = "getTooltip(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/client/item/TooltipContext;)Ljava/util/List;",
            at = @At("TAIL"), cancellable = true)
    private void getTooltipInject(PlayerEntity player, TooltipContext context, final CallbackInfoReturnable<List<Text>> info) {
        if (Palladium.getInstance().getModuleManager().isModuleEnabled(TooltipsModule.class)) {
            List<Text> newTooltip = TooltipsCallback.EVENT.invoker().getNewTooltip(self(), info.getReturnValue());
            info.setReturnValue(newTooltip);
        }
    }

    @Inject(method = "appendEnchantments(Ljava/util/List;Lnet/minecraft/nbt/ListTag;)V",
            at = @At("HEAD"), cancellable = true)
    private static void appendEnchantmentsInject(List<Text> tooltip, ListTag enchantments, final CallbackInfo info) {
        if (Palladium.getInstance().getModuleManager().isModuleEnabled(TooltipsModule.class)) {
            Optional<List<Text>> optional = EnchantmentTooltipsCallback.EVENT.invoker().getNewTooltip(enchantments);
            if (optional.isPresent()) {
                tooltip.addAll(optional.get());
            }
            info.cancel();
        }
    }
}
