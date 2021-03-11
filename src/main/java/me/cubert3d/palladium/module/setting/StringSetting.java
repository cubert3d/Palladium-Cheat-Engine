package me.cubert3d.palladium.module.setting;

public final class StringSetting extends AbstractSetting<String> {

    public enum CaseSensitivity {
        CASE_SENSITIVE,         // The string in this setting is case-sensitive.
        CASE_INSENSITIVE_LOWER, // The string in this setting is case-insensitive, and it defaults to lowercase.
        CASE_INSENSITIVE_UPPER  // The string in this setting is case-insensitive, and it defaults to uppercase.
    }

    private final CaseSensitivity caseSensitivity;
    private final int maxLength;

    public StringSetting(String name, String defaultValue, CaseSensitivity caseSensitivity, int maxLength) {
        super(name, defaultValue);
        this.caseSensitivity = caseSensitivity;
        this.maxLength = maxLength;

        if (maxLength < 1)
            throw new IllegalArgumentException();
    }

    public final CaseSensitivity getCaseSensitivity() {
        return caseSensitivity;
    }

    public final int getMaxLength() {
        return maxLength;
    }

    @Override
    public String getValue() {
        switch (caseSensitivity) {
            default:
            case CASE_SENSITIVE:
                return super.getValue();
            case CASE_INSENSITIVE_LOWER:
                return super.getValue().toLowerCase();
            case CASE_INSENSITIVE_UPPER:
                return super.getValue().toUpperCase();
        }
    }

    @Override
    public void setValue(String value) {
        // Truncate anything that exceeds the maximum string length
        value = value.substring(0, maxLength);

        switch (caseSensitivity) {
            case CASE_SENSITIVE:
                super.setValue(value);
                break;
            case CASE_INSENSITIVE_LOWER:
                super.setValue(value.toLowerCase());
                break;
            case CASE_INSENSITIVE_UPPER:
                super.setValue(value.toUpperCase());
                break;
        }
    }
}
