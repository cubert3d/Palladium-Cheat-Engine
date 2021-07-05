package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.callback.ItemStackDamageCallback;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.modules.render.TooltipsModule;
import me.cubert3d.palladium.util.StringUtil;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/8/2021",
        status = "in-progress"
)

@Mixin(ItemStack.class)
abstract class ItemStackMixin {

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

    @Inject(method = "damage(ILjava/util/Random;Lnet/minecraft/server/network/ServerPlayerEntity;)Z",
            at = @At(value = "TAIL"))
    private void damageInject(int amount, Random random, @Nullable ServerPlayerEntity player,
                              CallbackInfoReturnable<Boolean> info) {
        ActionResult result = ItemStackDamageCallback.EVENT.invoker().interact(player, (ItemStack) (Object) this);
    }

    @Inject(method = "getTooltip(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/client/item/TooltipContext;)Ljava/util/List;",
            at = @At("TAIL"), cancellable = true)
    private void getTooltipInject(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> info) {
        if (ModuleManager.isModuleEnabled(TooltipsModule.class)) {

            ItemStack stack = (ItemStack) (Object) this;
            List<Text> newTooltip = info.getReturnValue();

            if (stack.isDamageable()) {
                int maxDurability = stack.getMaxDamage();
                int durability = maxDurability - stack.getDamage();
                newTooltip.add(new LiteralText(""));
                newTooltip.add(new LiteralText("§7Durability: " + durability + "/" + maxDurability));
            }
            
            info.setReturnValue(newTooltip);
        }
    }

    @Inject(method = "appendEnchantments(Ljava/util/List;Lnet/minecraft/nbt/ListTag;)V",
            at = @At("HEAD"), cancellable = true)
    private static void appendEnchantmentsInject(List<Text> tooltip, ListTag enchantments, CallbackInfo info) {
        if (ModuleManager.isModuleEnabled(TooltipsModule.class)) {
            for (int i = 0; i < enchantments.size(); i++) {
                CompoundTag tag = enchantments.getCompound(i);
                Identifier id = Identifier.tryParse(tag.getString("id"));
                Optional<Enchantment> optional = Registry.ENCHANTMENT.getOrEmpty(id);
                if (optional.isPresent()) {
                    String entry = getEnchantmentEntry(optional.get(), tag.getInt("lvl"));
                    tooltip.add(new LiteralText(entry));
                }
            }

            info.cancel();
        }
    }

    private static String getEnchantmentEntry(Enchantment enchantment, int level) {
        String entry = getEnchantmentName(enchantment);
        if (level != 1 || enchantment.getMaxLevel() != 1) {
            entry = entry.concat(" " + convertArabicToRoman(level));
        }
        return entry;
    }

    private static String getEnchantmentName(@NotNull Enchantment enchantment) {
        switch (enchantment.getTranslationKey()) {
            case "enchantment.minecraft.protection":
                return "Protection";
            case "enchantment.minecraft.fire_protection":
                return "Fire Protection";
            case "enchantment.minecraft.feather_falling":
                return "Feather Falling";
            case "enchantment.minecraft.blast_protection":
                return "Blast Protection";
            case "enchantment.minecraft.projectile_protection":
                return "Projectile Protection";
            case "enchantment.minecraft.respiration":
                return "Respiration";
            case "enchantment.minecraft.aqua_affinity":
                return "Aqua Affinity";
            case "enchantment.minecraft.thorns":
                return "Thorns";
            case "enchantment.minecraft.depth_strider":
                return "Depth Strider";
            case "enchantment.minecraft.frost_walker":
                return "Frost Walker";
            case "enchantment.minecraft.binding_curse":
                return "§cCurse of Binding";
            case "enchantment.minecraft.soul_speed":
                return "Soul Speed";
            case "enchantment.minecraft.sharpness":
                return "Sharpness";
            case "enchantment.minecraft.smite":
                return "Smite";
            case "enchantment.minecraft.bane_of_arthropods":
                return "Bane of Arthropods";
            case "enchantment.minecraft.knockback":
                return "Knockback";
            case "enchantment.minecraft.fire_aspect":
                return "Fire Aspect";
            case "enchantment.minecraft.looting":
                return "Looting";
            case "enchantment.minecraft.sweeping":
                return "Sweeping";
            case "enchantment.minecraft.efficiency":
                return "Efficiency";
            case "enchantment.minecraft.silk_touch":
                return "Silk Touch";
            case "enchantment.minecraft.unbreaking":
                return "Unbreaking";
            case "enchantment.minecraft.fortune":
                return "Fortune";
            case "enchantment.minecraft.power":
                return "Power";
            case "enchantment.minecraft.punch":
                return "Punch";
            case "enchantment.minecraft.flame":
                return "Flame";
            case "enchantment.minecraft.infinity":
                return "Infinity";
            case "enchantment.minecraft.luck_of_the_sea":
                return "Luck of the Sea";
            case "enchantment.minecraft.lure":
                return "Lure";
            case "enchantment.minecraft.loyalty":
                return "Loyalty";
            case "enchantment.minecraft.impaling":
                return "Impaling";
            case "enchantment.minecraft.riptide":
                return "Riptide";
            case "enchantment.minecraft.channeling":
                return "Channeling";
            case "enchantment.minecraft.multishot":
                return "Multishot";
            case "enchantment.minecraft.quick_charge":
                return "Quick Charge";
            case "enchantment.minecraft.piercing":
                return "Piercing";
            case "enchantment.minecraft.mending":
                return "Mending";
            case "enchantment.minecraft.vanishing_curse":
                return "§cCurse of Vanishing";
            default:
                return enchantment.getTranslationKey();
        }
    }

    private static @NotNull String convertArabicToRoman(int number) {
        switch (number) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            case 6:
                return "VI";
            case 7:
                return "VII";
            case 8:
                return "VIII";
            case 9:
                return "IX";
            case 10:
                return "X";
            default:
                return Integer.valueOf(number).toString();
        }
    }
}
