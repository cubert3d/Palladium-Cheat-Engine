package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.util.Common;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.module.setting.Setting;
import me.cubert3d.palladium.module.setting.SettingType;
import me.cubert3d.palladium.module.setting.single.DoubleSetting;
import me.cubert3d.palladium.util.annotation.ClassDescription;

import java.util.Optional;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/9/2021"
)

public final class FullBrightModule extends Module {

    private static final double fullGamma = 10.0;
    private double prevGamma = 0.0;

    public FullBrightModule() {
        super("FullBright", "Fully illuminates all blocks.", ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
        this.addSetting(new DoubleSetting("Brightness", 10.0, 10.0));
    }

    @Override
    public Optional<String> getPrimaryInfo() {
        return Optional.ofNullable(getSetting("Brightness").asDoubleSetting().getValue().toString());
    }

    @Override
    protected void onChangeSetting() {
        if (isEnabled())
            updateGamma();
    }

    @Override
    protected void onEnable() {
        prevGamma = Common.getMC().options.gamma;
        updateGamma();
    }

    @Override
    protected void onDisable() {
        Common.getMC().options.gamma = prevGamma;
    }

    private void updateGamma() {
        double newGamma = fullGamma;
        Optional<Setting> optionalSetting = this.getSettingOptional("Brightness");

        if (optionalSetting.isPresent() && optionalSetting.get().getType().equals(SettingType.DOUBLE)) {
            newGamma = ((DoubleSetting) optionalSetting.get()).getValue();
        }

        Common.getMC().options.gamma = newGamma;
    }
}
