package me.cubert3d.palladium.module.setting;

import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/4/2021",
        status = "complete"
)

public final class BooleanSetting extends AbstractSetting<Boolean> {

    public BooleanSetting(String name, Boolean defaultValue) {
        super(name, defaultValue);
    }

    public void toggle() {
        setValue(!getValue());
    }
}
