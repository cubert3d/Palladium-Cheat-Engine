package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.event.mixin.mixins.ItemStackMixin;
import me.cubert3d.palladium.util.annotation.CallbackInfo;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.annotation.Interaction;
import me.cubert3d.palladium.util.annotation.Listener;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

@ClassInfo(
        authors = "cubert3d",
        date = "7/20/2021",
        type = ClassType.CALLBACK
)

@CallbackInfo(
        returns = List.class,
        listeners = {
                @Listener(where = TooltipsCallback.class)
        },
        interactions = {
                @Interaction(where = ItemStackMixin.class, method = "getTooltipInject")
        }
)

public interface TooltipsCallback {

    Event<TooltipsCallback> EVENT = EventFactory.createArrayBacked(TooltipsCallback.class,
            listeners -> (stack, oldTooltip) -> {
                List<Text> newToolTip = new ArrayList<>();
                for (TooltipsCallback listener : listeners) {
                    newToolTip.addAll(listener.getNewTooltip(stack, oldTooltip));
                }
                return newToolTip;
            });

    List<Text> getNewTooltip(ItemStack stack, List<Text> oldTooltip);
}
