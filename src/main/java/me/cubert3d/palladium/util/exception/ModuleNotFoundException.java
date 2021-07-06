package me.cubert3d.palladium.util.exception;

public final class ModuleNotFoundException extends RuntimeException {

    public ModuleNotFoundException() {

    }

    public ModuleNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
