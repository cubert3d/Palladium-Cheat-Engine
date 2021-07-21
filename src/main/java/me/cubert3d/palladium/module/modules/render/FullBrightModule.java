package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.module.setting.single.DoubleSetting;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;

import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "3/9/2021",
        type = ClassType.MODULE
)

public final class FullBrightModule extends ToggleModule {

    private static final double fullGamma = 10.0;
    private double prevGamma = 0.0;
    private final DoubleSetting gamma;

    public FullBrightModule() {
        super("FullBright", "Fully illuminates all blocks.");
        this.gamma = new DoubleSetting("Gamma", "The brightness enforced by FullBright.", 10.0, 10.0);
        this.addSetting(gamma);
    }

    @Override
    public Optional<String> getPrimaryInfo() {
        return Optional.ofNullable(gamma.getAsString());
    }

    @Override
    protected void onChangeSetting() {
        if (isEnabled()) {
            updateGamma();
        }
    }

    @Override
    protected void onEnable() {
        prevGamma = MinecraftClient.getInstance().options.gamma;
        updateGamma();
    }

    @Override
    protected void onDisable() {
        MinecraftClient.getInstance().options.gamma = prevGamma;
    }

    private void updateGamma() {
        MinecraftClient.getInstance().options.gamma = gamma.getValue();
    }
}
