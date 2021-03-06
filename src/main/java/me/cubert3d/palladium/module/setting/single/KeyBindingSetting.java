package me.cubert3d.palladium.module.setting.single;

import me.cubert3d.palladium.module.setting.SettingType;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.exception.SettingParseException;
import me.cubert3d.palladium.util.input.Keys;
import net.minecraft.client.util.InputUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "6/29/2021",
        type = ClassType.SETTING
)

public final class KeyBindingSetting extends SingleSetting<InputUtil.Key> {

    public KeyBindingSetting(final String name, final String description, InputUtil.Key defaultValue) {
        super(name, description, defaultValue);
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
