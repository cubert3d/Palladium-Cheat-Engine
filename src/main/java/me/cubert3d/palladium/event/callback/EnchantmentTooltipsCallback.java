package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.event.mixin.mixins.ItemStackMixin;
import me.cubert3d.palladium.util.annotation.CallbackInfo;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.annotation.Interaction;
import me.cubert3d.palladium.util.annotation.Listener;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "7/20/2021",
        type = ClassType.CALLBACK
)

@CallbackInfo(
        returns = Optional.class,
        listeners = {
                @Listener(where = TooltipsCallback.class)
        },
        interactions = {
                @Interaction(where = ItemStackMixin.class, method = "appendEnchantmentsInject")
        }
)

public interface EnchantmentTooltipsCallback {

    Event<EnchantmentTooltipsCallback> EVENT = EventFactory.createArrayBacked(EnchantmentTooltipsCallback.class,
            listeners -> enchantments -> {
                List<Text> newToolTip = new ArrayList<>();
                for (EnchantmentTooltipsCallback listener : listeners) {
                    Optional<List<Text>> optional = listener.getNewTooltip(enchantments);
                    optional.ifPresent(newToolTip::addAll);
                }
                if (newToolTip.size() > 0) {
                    return Optional.of(newToolTip);
                }
                else {
                    return Optional.empty();
                }
            });

    Optional<List<Text>> getNewTooltip(ListTag enchantments);
}
