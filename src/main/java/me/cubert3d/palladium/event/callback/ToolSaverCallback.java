package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.event.mixin.mixins.ClientPlayerInteractionManagerMixin;
import me.cubert3d.palladium.event.mixin.mixins.LivingEntityMixin;
import me.cubert3d.palladium.module.modules.player.ToolSaverModule;
import me.cubert3d.palladium.util.annotation.CallbackInfo;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.annotation.Interaction;
import me.cubert3d.palladium.util.annotation.Listener;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@ClassInfo(
        authors = "cubert3d",
        date = "7/14/2021",
        type = ClassType.CALLBACK
)

@CallbackInfo(
        listeners = {
                @Listener(where = ToolSaverModule.class)
        },
        interactions = {
                @Interaction(where = ClientPlayerInteractionManagerMixin.class, method = {"attackBlockInject", "interactBlockInject", "interactItemInject", "attackEntityInject", "interactEntityInject"}),
                @Interaction(where = LivingEntityMixin.class, method = "damageInject")
        }
)

public interface ToolSaverCallback {
    /*
    Places where this needs to be interacted with:

    when a mining tool is used to mine a block (DONE)
    when a hoe is used to till dirt (DONE)
    when shears are used to shear sheep (DONE)
    when a fishing rod is used (DONE)
    when a damageable item is used to attack a mob (DONE)
    when an arrow is loosed from a bow or crossbow (DONE)
    when armor is damaged (DONE)
    when an elytra is used up
     */
    Event<ToolSaverCallback> EVENT = EventFactory.createArrayBacked(ToolSaverCallback.class,
            listeners -> armorSwap -> {
                for (ToolSaverCallback listener : listeners) {
                    listener.interact(armorSwap);
                }
            });

    void interact(boolean armorSwap);
}
