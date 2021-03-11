package me.cubert3d.palladium.module.setting;

public enum SettingType {
    BOOLEAN(false),
    INTEGER(true),
    DOUBLE(true),
    STRING(false);

    private final boolean numberType;

    SettingType(boolean numberType) {
        this.numberType = numberType;
    }

    public final boolean isNumberType() {
        return numberType;
    }
}
