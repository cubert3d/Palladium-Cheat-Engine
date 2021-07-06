package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.gui.text.provider.EffectListProvider;
import me.cubert3d.palladium.gui.text.provider.TextProvider;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/17/2021"
)

public final class EffectListModule extends ToggleModule {

    private static final TextProvider effectList = new EffectListProvider();

    public EffectListModule() {
        super("Effects", "Displays the player's current effects and their remaining duration.", ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }

    @Override
    protected void onEnable() {
        Palladium.getInstance().getGuiRenderer().getTextHudRenderer().getTextManager().setTopRightList(effectList);
    }

    @Override
    protected void onDisable() {
        Palladium.getInstance().getGuiRenderer().getTextHudRenderer().getTextManager().clearTopRightList();
    }
}
