package me.cubert3d.palladium.input;

import me.cubert3d.palladium.event.mixin.KeyBindingAccessor;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class PalladiumKeyBinding {

    private static final String palladiumKeyCategory = "key.categories.palladium";

    private final KeyBinding binding;

    // Whether this key-binding's method is triggered once
    // upon being pressed, or continues to fire as it is held.
    private final Type type;
    private boolean wasReleased = true;

    public PalladiumKeyBinding(String translationKey, int code, Type type) {
        this(translationKey, InputUtil.Type.KEYSYM, code, type);
    }

    public PalladiumKeyBinding(String translationKey, InputUtil.Type inputUtilType, int code, Type type) {
        this.binding = new KeyBinding(translationKey, inputUtilType, code, palladiumKeyCategory);
        this.type = type;
    }

    public final Type getType() {
        return type;
    }

    public final String getTranslationKey() {
        return this.binding.getTranslationKey();
    }

    public final InputUtil.Key getBoundKey() {
        return ((KeyBindingAccessor) this.binding).getBoundKey();
    }

    public final void setBoundKey(InputUtil.Key boundKey) {
        this.binding.setBoundKey(boundKey);
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

    /*
     Determines which of the three trigger methods, if any at all,
     should be called, depending on the trigger type of this binding
     (press-once, press-and-release, and hold), and whether the
     corresponding key is being pressed, held, or released.
    */
    public final void trigger() {
        switch (type) {
            case PRESS_ONCE:

                if (wasReleased) {
                    onPressed();
                    setReleased(false);
                }
                else if (!isPressed() && wasPressed()) {
                    setReleased(true);
                }
                break;

            case PRESS_AND_RELEASE:

                if (wasReleased) {
                    onPressed();
                    setReleased(false);
                }
                else if (!isPressed() && wasPressed()) {
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
                else if (wasPressed()) {
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
