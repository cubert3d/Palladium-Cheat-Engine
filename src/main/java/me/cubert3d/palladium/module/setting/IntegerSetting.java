package me.cubert3d.palladium.module.setting;

public final class IntegerSetting extends NumberSetting<Integer> {

    public IntegerSetting(String name, int defaultValue, int minValue, int maxValue) {
        super(name, defaultValue, minValue, maxValue);
    }

    @Override
    public void setValue(Integer value) {
        // If the given value is below the minimum allowed value, then just pass that lower bound instead.
        if (value < getMinValue())
            super.setValue(getMinValue());
        // If it is above the maximum, then pass that upper bound instead.
        else if (value > getMaxValue())
            super.setValue(getMaxValue());
        // If it is within the bounds, then go ahead and pass it.
        else
            super.setValue(value);
    }

    @Override
    public void add(Integer addend) {
        setValue(getValue() + addend);
    }

    @Override
    public void subtract(Integer subtrahend) {
        setValue(getValue() - subtrahend);
    }
}
