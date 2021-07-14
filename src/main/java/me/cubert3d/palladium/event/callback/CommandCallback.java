package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.ActionResult;

@ClassInfo(
        authors = "cubert3d",
        date = "3/2/2021",
        type = ClassType.CALLBACK
)

public interface CommandCallback {
    /*
    Used in the command listener.
     */
    Event<CommandCallback> EVENT = EventFactory.createArrayBacked(CommandCallback.class,
            (listeners) -> (player, message) -> {
                for (CommandCallback listener : listeners) {
                    ActionResult result = listener.interact(player, message);

                    if (result != ActionResult.PASS)
                        return result;
                }

                return ActionResult.PASS;
            });

    ActionResult interact(ClientPlayerEntity player, String message);
}
