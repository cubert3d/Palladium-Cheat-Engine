package me.cubert3d.palladium.event.callback;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

@ClassInfo(
        authors = "cubert3d",
        date = "7/14/2021",
        type = ClassType.CALLBACK
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
    when armor is damaged
    when an elytra is used up
     */
    Event<ToolSaverCallback> EVENT = EventFactory.createArrayBacked(ToolSaverCallback.class,
            listeners -> armorSwap -> {
                for (ToolSaverCallback listener : listeners) {
                    ActionResult result = listener.interact(armorSwap);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact(boolean armorSwap);
}
