package me.cubert3d.palladium.module.setting;

public abstract class NumberSetting<N extends Number> extends AbstractSetting<N> {

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

    public abstract void add(N addend);
    public abstract void subtract(N subtrahend);
}
