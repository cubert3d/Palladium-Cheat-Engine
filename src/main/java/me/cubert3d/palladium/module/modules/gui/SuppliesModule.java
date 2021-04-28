package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.gui.TextHudRenderer;
import me.cubert3d.palladium.gui.text.provider.SuppliesProvider;
import me.cubert3d.palladium.gui.text.provider.TextProvider;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.module.setting.list.ItemListSetting;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/10/2021"
)

public final class SuppliesModule extends Module {

    public static final TextProvider suppliesList = new SuppliesProvider();

    public SuppliesModule() {
        super("Supplies", "Displays important supplies and their quantities you have on-screen.",
                ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
        this.addSetting(new ItemListSetting("Items"));
    }

    @Override
    protected void onEnable() {
        TextHudRenderer.getTextManager().setBottomRightList(suppliesList);
    }

    @Override
    protected void onDisable() {
        TextHudRenderer.getTextManager().clearBottomRightList();
    }
}
