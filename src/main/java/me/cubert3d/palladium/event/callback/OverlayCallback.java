package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.module.modules.render.AntiOverlayModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

@ClassInfo(
        authors = "cubert3d",
        date = "4/8/2021",
        type = ClassType.CALLBACK
)

public interface OverlayCallback {

    Event<OverlayCallback> EVENT = EventFactory.createArrayBacked(OverlayCallback.class,
            (listeners) -> (overlay) -> {
                for (OverlayCallback listener : listeners) {
                    ActionResult result = listener.interact(overlay);

                    if (result != ActionResult.PASS)
                        return result;
                }
                return ActionResult.PASS;
            });

    ActionResult interact(AntiOverlayModule.Overlay overlay);
}
