package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.module.setting.SettingType;
import me.cubert3d.palladium.module.setting.Setting;
import me.cubert3d.palladium.util.annotation.ClassDescription;

import java.util.Optional;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/9/2021",
        status = "complete"
)

public final class FullBrightModule extends Module {

    private static final double fullGamma = 10.0;
    private static final double defaultGamma = 0.0;
    private double prevGamma = 0.0;

    public FullBrightModule() {
        super("FullBright", "Fully illuminates all blocks.", ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
        addSetting(new Setting("Gamma", SettingType.DOUBLE, 10.0));
    }

    @Override
    protected void onChangeSetting() {
        setGamma();
    }

    @Override
    protected void onEnable() {
        prevGamma = Common.getMC().options.gamma;
        setGamma();
    }

    @Override
    protected void onDisable() {
        Common.getMC().options.gamma = prevGamma;
    }

    private void setGamma() {
        double newGamma = fullGamma;
        Optional<Setting> optional = this.getSetting("Gamma");

        if (optional.isPresent())
            newGamma = (double) optional.get().getValue();

        Common.getMC().options.gamma = newGamma;
    }
}
