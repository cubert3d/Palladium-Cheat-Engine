package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.module.AbstractModule;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;

public final class FullBrightModule extends AbstractModule {

    private static final double fullGamma = 10.0;
    private double prevGamma = 0.0;

    public FullBrightModule() {
        super("FullBright", "Fully illuminates all blocks.", ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
    }

    @Override
    protected void onEnable() {
        prevGamma = Common.getMC().options.gamma;
        Common.getMC().options.gamma = fullGamma;
    }

    @Override
    protected void onDisable() {
        Common.getMC().options.gamma = prevGamma;
    }
}
