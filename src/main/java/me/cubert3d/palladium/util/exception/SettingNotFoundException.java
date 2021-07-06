package me.cubert3d.palladium.util.exception;

public final class SettingNotFoundException extends RuntimeException {

    public SettingNotFoundException() {

    }

    public SettingNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
