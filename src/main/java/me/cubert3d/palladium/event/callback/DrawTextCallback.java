package me.cubert3d.palladium.event.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

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
