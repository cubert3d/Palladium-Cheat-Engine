package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.event.mixin.mixins.ClientPlayNetworkHandlerMixin;
import me.cubert3d.palladium.module.modules.combat.KillAuraModule;
import me.cubert3d.palladium.module.modules.player.BlinkModule;
import me.cubert3d.palladium.module.modules.render.FreecamModule;
import me.cubert3d.palladium.util.annotation.CallbackInfo;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.annotation.Interaction;
import me.cubert3d.palladium.util.annotation.Listener;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.Packet;

@ClassInfo(
        authors = "cubert3d",
        date = "7/19/2021",
        type = ClassType.MIXIN
)

@CallbackInfo(
        returns = Boolean.class,
        listeners = {
                @Listener(where = KillAuraModule.class),
                @Listener(where = BlinkModule.class),
                @Listener(where = FreecamModule.class)
        },
        interactions = {
                @Interaction(where = ClientPlayNetworkHandlerMixin.class, method = "sendPacketInject")
        }
)

public interface SendPacketCallback {

    Event<SendPacketCallback> EVENT = EventFactory.createArrayBacked(SendPacketCallback.class,
            listeners -> packet -> {
                for (SendPacketCallback listener : listeners) {
                    boolean shouldCancel = listener.shouldCancel(packet);
                    if (shouldCancel) {
                        return true;
                    }
                }
                return false;
            });

    boolean shouldCancel(Packet<?> packet);
}
