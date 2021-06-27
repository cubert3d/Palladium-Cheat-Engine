package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.gui.TextHudRenderer;
import me.cubert3d.palladium.gui.text.provider.EffectListProvider;
import me.cubert3d.palladium.gui.text.provider.TextProvider;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/17/2021"
)

public final class EffectListModule extends Module {

    private static final TextProvider effectList = new EffectListProvider();

    public EffectListModule() {
        super("Effects", "Displays the player's current effects and their remaining duration.",
                ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }

    @Override
    protected void onEnable() {
        TextHudRenderer.getTextManager().setTopRightList(effectList);
    }

    @Override
    protected void onDisable() {
        TextHudRenderer.getTextManager().clearTopRightList();
    }
}
