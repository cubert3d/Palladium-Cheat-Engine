package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.event.mixin.mixins.ClientPlayerEntityMixin;
import me.cubert3d.palladium.event.mixin.mixins.InGameHudMixin;
import me.cubert3d.palladium.event.mixin.mixins.LivingEntityMixin;
import me.cubert3d.palladium.module.modules.render.AntiOverlayModule;
import me.cubert3d.palladium.util.annotation.CallbackInfo;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.annotation.Interaction;
import me.cubert3d.palladium.util.annotation.Listener;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@ClassInfo(
        authors = "cubert3d",
        date = "4/8/2021",
        type = ClassType.CALLBACK
)

@CallbackInfo(
        returns = Boolean.class,
        listeners = {
                @Listener(where = AntiOverlayModule.class)
        },
        interactions = {
                @Interaction(where = ClientPlayerEntityMixin.class, method = "updateNauseaInject"),
                @Interaction(where = InGameHudMixin.class, method = {"renderPumpkinOverlayInject", "renderPortalOverlayInject"}),
                @Interaction(where = LivingEntityMixin.class, method = "hasStatusEffectInject")
        }
)

public interface OverlayCallback {

    Event<OverlayCallback> EVENT = EventFactory.createArrayBacked(OverlayCallback.class,
            (listeners) -> (overlay) -> {
                for (OverlayCallback listener : listeners) {
                    boolean hideOverlay = listener.shouldHideOverlay(overlay);
                    if (hideOverlay) {
                        return true;
                    }
                }
                return false;
            });

    boolean shouldHideOverlay(AntiOverlayModule.Overlay overlay);
}
