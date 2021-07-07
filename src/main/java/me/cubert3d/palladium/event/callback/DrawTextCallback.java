package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import me.cubert3d.palladium.util.annotation.DebugOnly;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.ActionResult;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/8/2021",
        status = "complete"
)

@DebugOnly
public interface DrawTextCallback {
    Event<DrawTextCallback> EVENT = EventFactory.createArrayBacked(DrawTextCallback.class,
            (listeners) -> (matrices, text, x, y, color) -> {
                for (DrawTextCallback listener : listeners) {
                    ActionResult result = listener.interact(matrices, text, x, y, color);

                    if (result != ActionResult.PASS)
                        return result;
                }

                return ActionResult.PASS;
            });

    ActionResult interact(MatrixStack matrices, String text, float x, float y, int color);
}
