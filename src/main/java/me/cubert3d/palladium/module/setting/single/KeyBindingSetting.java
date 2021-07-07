package me.cubert3d.palladium.module.setting.single;

import me.cubert3d.palladium.input.Keys;
import me.cubert3d.palladium.module.setting.SettingType;
import me.cubert3d.palladium.util.exception.SettingParseException;
import net.minecraft.client.util.InputUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class KeyBindingSetting extends SingleSetting<InputUtil.Key> {

    public KeyBindingSetting(String name, InputUtil.Key defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public final boolean isSet() {
        return getValue() != null;
    }

    @Override
    public final SettingType getType() {
        return SettingType.KEY_BINDING;
    }

    @Override
    protected final void setValue(InputUtil.Key value) {
        super.setValue(value);
    }

    @Override
    public final String getAsString() {
        if (isSet()) {
            return getValue().getTranslationKey();
        }
        else {
            return "";
        }
    }

    @Override
    public final void setFromString(@NotNull String string) {
        if (string.trim().equals("")) {
            setValue(null);
        }
        else {
            Optional<InputUtil.Key> optional = parseString(string);
            if (optional.isPresent()) {
                setValue(optional.get());
            }
            else {
                throw new SettingParseException();
            }
        }
    }

    @Override
    protected final Optional<InputUtil.Key> parseString(String string) {
        return Keys.getKeyFromString(string);
    }
}
