package me.cubert3d.palladium.module;

import me.cubert3d.palladium.Main;
import me.cubert3d.palladium.module.setting.Setting;
import me.cubert3d.palladium.util.Named;
import me.cubert3d.palladium.util.annotation.ClassDescription;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/3/2021",
        status = "in-progress"
)

public abstract class Module implements Named {

    // The name of this module must be unique
    private final String name;
    // Should be brief and concise
    private final String description;

    private final ModuleType moduleType;
    private boolean enabled;
    protected final Set<Setting> settings = new HashSet<>();
    private final ModuleDevStatus status;

    protected Module(String name, String description, ModuleType moduleType, ModuleDevStatus status) {
        this.name = name;
        this.description = description;
        this.moduleType = moduleType;
        this.enabled = false;
        this.status = status;
    }

    @Override
    public final boolean equals(Object obj) {
        // Two modules should be equal if their names are equal, case-sensitively.
        if (obj instanceof Module) {
            Module otherModule = (Module) obj;
            return this.getName().equals(otherModule.getName());
        }
        return false;
    }

    @Override
    public final String getName() {
        return name;
    }

    public final String getDescription() {
        return description;
    }

    public final ModuleType getType() {
        return moduleType;
    }

    public final ModuleDevStatus getDevStatus() {
        return status;
    }

    public final boolean isAvailable() {
        return status.equals(ModuleDevStatus.AVAILABLE) || Main.isDebugModeEnabled();
    }



    // ENABLE

    public final boolean isEnabled() {
        if (moduleType.equals(ModuleType.TOGGLE))
            return enabled;
        else
            return false;
    }

    public final void enable() {
        if (status.equals(ModuleDevStatus.AVAILABLE) || Main.isDebugModeEnabled()) {
            if (moduleType.equals(ModuleType.TOGGLE)) {
                enabled = true;
                onEnable();
            }
        }
    }

    public final void disable() {
        if (status.equals(ModuleDevStatus.AVAILABLE) || Main.isDebugModeEnabled()) {
            if (moduleType.equals(ModuleType.TOGGLE)) {
                enabled = false;
                onDisable();
            }
        }
    }

    public final boolean toggle() {
        if (moduleType.equals(ModuleType.TOGGLE)) {
            if (isEnabled())
                disable();
            else
                enable();
            return isEnabled();
        }
        return false;
    }

    public final void resetEnabled() {
        if (moduleType.equals(ModuleType.TOGGLE)) {
            disable();
        }
    }



    // SETTING

    protected final void addSetting(Setting setting) {
        this.settings.add(setting);
    }

    public final Optional<Setting> getSetting(String name) {

        Optional<Setting> optional = Optional.empty();

        for (Setting setting : settings) {
            if (setting.getName().equalsIgnoreCase(name)) {
                optional = Optional.of(setting);
                break;
            }
        }
        return optional;
    }

    public boolean changeSetting(String name, Object value) {
        for (Setting setting : settings) {
            if (setting.getName().equalsIgnoreCase(name))
                return setting.setValue(value);
        }
        return false;
    }

    public boolean changeSettingWithString(String name, String value) {
        boolean isSuccessful = false;
        for (Setting setting : settings) {
            if (setting.getName().equalsIgnoreCase(name))
                isSuccessful = setting.setValueFromString(value);
                onChangeSetting();
                return isSuccessful;
        }
        return isSuccessful;
    }

    protected void onChangeSetting() {

    }



    // LOAD

    protected void onLoad() {}

    protected void onEnable() {}

    protected void onDisable() {}

    public void execute(String[] args) {}
}
