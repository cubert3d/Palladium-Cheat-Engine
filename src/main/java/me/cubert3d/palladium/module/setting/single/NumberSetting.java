package me.cubert3d.palladium.module.setting.single;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "4/7/2021",
        type = ClassType.SETTING
)

public abstract class NumberSetting<N extends Number> extends SingleSetting<N> {

    private final N minValue;
    private final N maxValue;

    protected NumberSetting(String name, N defaultValue, N minValue, N maxValue) {
        super(name, defaultValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public final N getMinValue() {
        return minValue;
    }

    public final N getMaxValue() {
        return maxValue;
    }
}
