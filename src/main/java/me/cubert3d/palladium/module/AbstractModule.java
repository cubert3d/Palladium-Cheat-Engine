package me.cubert3d.palladium.module;

import me.cubert3d.palladium.Main;
import me.cubert3d.palladium.module.setting.BooleanSetting;
import me.cubert3d.palladium.util.Named;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/3/2021",
        status = "in-progress"
)

public abstract class AbstractModule implements Named {

    // The name of this module must be unique
    private final String name;
    // Should be brief and concise
    private final String description;

    private final ModuleType moduleType;
    private final BooleanSetting enabledSetting = new BooleanSetting("Enabled", false);

    private final ModuleDevStatus status;

    protected AbstractModule(String name, String description, ModuleType moduleType, ModuleDevStatus status) {
        this.name = name;
        this.description = description;
        this.moduleType = moduleType;
        this.status = status;
    }

    @Override
    public final boolean equals(Object obj) {
        // Two modules should be equal if their names are equal, case-sensitively.
        if (obj instanceof AbstractModule) {
            AbstractModule otherModule = (AbstractModule) obj;
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



    public final boolean isEnabled() {
        if (moduleType.equals(ModuleType.TOGGLE))
            return enabledSetting.getValue();
        else
            return false;
    }

    public final void enable() {
        if (status.equals(ModuleDevStatus.AVAILABLE) || Main.isDebugModeEnabled()) {
            if (moduleType.equals(ModuleType.TOGGLE)) {
                enabledSetting.setValue(true);
                onEnable();
            }
        }
    }

    public final void disable() {
        if (status.equals(ModuleDevStatus.AVAILABLE) || Main.isDebugModeEnabled()) {
            if (moduleType.equals(ModuleType.TOGGLE)) {
                enabledSetting.setValue(false);
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
            enabledSetting.resetValue();
        }
    }



    protected void onLoad() {}

    protected void onEnable() {}

    protected void onDisable() {}

    public void execute(String[] args) {}
}
