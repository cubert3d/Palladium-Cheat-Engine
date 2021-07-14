package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.gui.text.provider.SuppliesProvider;
import me.cubert3d.palladium.gui.text.provider.TextProvider;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.module.setting.list.ItemListSetting;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/10/2021"
)

public final class SuppliesModule extends ToggleModule {

    public static final TextProvider suppliesList = new SuppliesProvider();

    public SuppliesModule() {
        super("Supplies", "Displays important supplies and their quantities you have on-screen.");
        this.addSetting(new ItemListSetting("Items"));
    }

    @Override
    protected void onEnable() {
        Palladium.getInstance().getGuiRenderer().getTextHudRenderer().getTextManager().setBottomRightList(suppliesList);
    }

    @Override
    protected void onDisable() {
        Palladium.getInstance().getGuiRenderer().getTextHudRenderer().getTextManager().clearBottomRightList();
    }
}
