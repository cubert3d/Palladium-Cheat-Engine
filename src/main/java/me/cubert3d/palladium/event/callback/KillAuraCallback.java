package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.event.mixin.mixins.MinecraftClientMixin;
import me.cubert3d.palladium.module.modules.combat.KillAuraModule;
import me.cubert3d.palladium.util.annotation.CallbackInfo;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.annotation.Interaction;
import me.cubert3d.palladium.util.annotation.Listener;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@ClassInfo(
        authors = "cubert3d",
        date = "7/18/2021",
        type = ClassType.CALLBACK
)

@CallbackInfo(
        returns = Void.class,
        listeners = {
                @Listener(where = KillAuraModule.class)
        },
        interactions = {
                @Interaction(where = MinecraftClientMixin.class, method = "tickInject")
        }
)

public interface KillAuraCallback {

    Event<KillAuraCallback> EVENT = EventFactory.createArrayBacked(KillAuraCallback.class,
            listeners -> () -> {
                for (KillAuraCallback listener : listeners) {
                    listener.interact();
                }
            });

    void interact();
}
