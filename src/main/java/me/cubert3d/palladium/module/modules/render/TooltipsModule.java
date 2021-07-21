package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.event.callback.EnchantmentTooltipsCallback;
import me.cubert3d.palladium.event.callback.TooltipsCallback;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "7/4/2021",
        type = ClassType.MODULE
)

public final class TooltipsModule extends ToggleModule {

    public TooltipsModule() {
        super("Tooltips", "Displays extra information in item tooltips.");
    }

    @Override
    public void onLoad() {

        TooltipsCallback.EVENT.register((stack, oldTooltip) -> {
            if (isEnabled()) {
                if (stack.isDamageable()) {
                    int maxDurability = stack.getMaxDamage();
                    int durability = maxDurability - stack.getDamage();
                    oldTooltip.add(new LiteralText(""));
                    oldTooltip.add(new LiteralText("§7Durability: " + durability + "/" + maxDurability));
                }
            }
            return oldTooltip;
        });

        EnchantmentTooltipsCallback.EVENT.register(enchantments -> {
            if (isEnabled()) {
                List<Text> newTooltip = new ArrayList<>();
                for (int i = 0; i < enchantments.size(); i++) {
                    CompoundTag tag = enchantments.getCompound(i);
                    Identifier id = Identifier.tryParse(tag.getString("id"));
                    Optional<Enchantment> optional = Registry.ENCHANTMENT.getOrEmpty(id);
                    if (optional.isPresent()) {
                        String entry = getEnchantmentEntry(optional.get(), tag.getInt("lvl"));
                        newTooltip.add(new LiteralText(entry));
                    }
                }
                return Optional.of(newTooltip);
            }
            else {
                return Optional.empty();
            }
        });
    }

    private String getEnchantmentEntry(Enchantment enchantment, int level) {
        String entry = getEnchantmentName(enchantment);
        if (level != 1 || enchantment.getMaxLevel() != 1) {
            entry = entry.concat(" " + convertArabicToRoman(level));
        }
        return entry;
    }

    private String getEnchantmentName(@NotNull Enchantment enchantment) {
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

    private @NotNull String convertArabicToRoman(int number) {
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
