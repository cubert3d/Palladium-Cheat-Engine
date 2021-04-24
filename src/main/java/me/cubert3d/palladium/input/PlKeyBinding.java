package me.cubert3d.palladium.input;

import me.cubert3d.palladium.event.mixin.KeyBindingAccessor;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlKeyBinding {

    private static final String PALLADIUM_KEY_CATEGORY = "key.categories.palladium";
    private static final int UNBOUND_PLACEHOLDER = -1;

    private final KeyBinding binding;

    // Whether this key-binding's method is triggered once
    // upon being pressed, or continues to fire as it is held.
    private final Type type;
    private boolean isBound;
    private final boolean hasDefault;
    private boolean wasReleased = true;

    public PlKeyBinding(String translationKey, Type type) {
        this.binding = new KeyBinding(translationKey, InputUtil.Type.KEYSYM, UNBOUND_PLACEHOLDER, PALLADIUM_KEY_CATEGORY);
        this.type = type;
        this.isBound = false;
        this.hasDefault = false;
    }

    public PlKeyBinding(String translationKey, int code, Type type) {
        this.binding = new KeyBinding(translationKey, InputUtil.Type.KEYSYM, code, PALLADIUM_KEY_CATEGORY);
        this.type = type;
        this.isBound = true;
        this.hasDefault = true;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof PlKeyBinding) {
            PlKeyBinding other = (PlKeyBinding) obj;
            return this.getTranslationKey().equalsIgnoreCase(other.getTranslationKey());
        }
        return false;
    }

    @Override
    public final String toString() {
        return getTranslationKey();
    }



    public final String getTranslationKey() {
        return binding.getTranslationKey();
    }

    public final InputUtil.Key getDefaultKey() {
        return binding.getDefaultKey();
    }

    public final InputUtil.Key getBoundKey() {
        return ((KeyBindingAccessor) binding).getBoundKey();
    }

    private void setBoundKey(InputUtil.Key boundKey) {
        this.binding.setBoundKey(boundKey);
    }

    // Bound key setter for user input; uses an Optional from Keys.
    public final boolean setBoundKey(@NotNull String translationKey) {
        switch (translationKey) {
            case "none":
                setBound(false);
                return true;
            case "reset":
                resetBoundKey();
                return true;
            default:
                Optional<InputUtil.Key> optionalKey = Keys.getKey(translationKey);
                if (optionalKey.isPresent()) {
                    setBoundKey(optionalKey.get());
                    setBound(true);
                    return true;
                }
                else {
                    return false;
                }
        }
    }

    public final void resetBoundKey() {
        if (hasDefault) {
            setBoundKey(getDefaultKey());
            setBound(true);
        }
        else {
            setBound(false);
        }
    }

    public final Type getType() {
        return type;
    }

    public final boolean isBound() {
        return isBound;
    }

    public final void setBound(boolean bound) {
        isBound = bound;
    }

    public final int getKeyCode() {
        return getBoundKey().getCode();
    }



    private boolean isPressed() {
        return binding.isPressed();
    }

    private boolean wasPressed() {
        return binding.wasPressed();
    }

    protected final void setReleased(boolean wasReleased) {
        this.wasReleased = wasReleased;
    }

    public boolean shouldTrigger() {
        return isBound();
    }

    /*
     Determines which of the three trigger methods, if any at all,
     should be called, depending on the trigger type of this binding
     (press-once, press-and-release, and hold), and whether the
     corresponding key is being pressed, held, or released.
    */
    public final void trigger() {
        boolean wasPressed = wasPressed();
        switch (type) {
            case PRESS_ONCE:

                if (wasReleased) {
                    onPressed();
                    setReleased(false);
                }
                else if (!isPressed() && !wasPressed) {
                    setReleased(true);
                }
                break;

            case PRESS_AND_RELEASE:

                if (wasReleased) {
                    onPressed();
                    setReleased(false);
                }
                else if (!isPressed() && !wasPressed) {
                    onReleased();
                    setReleased(true);
                }
                break;

            case HOLD:

                if (wasReleased) {
                    onPressed();
                    setReleased(false);
                }
                else if (isPressed()) {
                    onHeld();
                }
                else if (!wasPressed) {
                    onReleased();
                    setReleased(true);
                }
                break;
        }
    }

    // Called when the key is first pressed down.
    protected void onPressed() {

    }

    // Called when the key is being held, even after being pressed.
    protected void onHeld() {

    }

    // Called when the key is released.
    protected void onReleased() {

    }

    public enum Type {
        PRESS_ONCE,
        PRESS_AND_RELEASE,
        HOLD
    }
}
