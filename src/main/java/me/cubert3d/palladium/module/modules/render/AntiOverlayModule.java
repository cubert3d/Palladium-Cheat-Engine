package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.event.callback.OverlayCallback;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.module.setting.single.BooleanSetting;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "3/10/2021",
        type = ClassType.MODULE
)

public final class AntiOverlayModule extends ToggleModule {

    public AntiOverlayModule() {
        super("AntiOverlay", "Removes obtrusive overlays.");
        this.addSetting(new BooleanSetting("Pumpkin", "Whether or not to hide the pumpkin overlay.", true));
        this.addSetting(new BooleanSetting("Portal", "Whether or not to hide the nether portal overlay.", true));
        this.addSetting(new BooleanSetting("Nausea", "Whether or not to remove the visual effect of nausea.", true));
        this.addSetting(new BooleanSetting("Blindness", "Whether or not to remove the visual effect of blindness.", true));
    }

    @Override
    public void onLoad() {
        OverlayCallback.EVENT.register(overlay -> isEnabled() && getSetting(overlay.getName()).asBooleanSetting().getValue());
    }

    public enum Overlay {
        PUMPKIN("Pumpkin"),
        PORTAL("Portal"),
        NAUSEA("Nausea"),
        BLINDNESS("Blindness");

        private final String name;

        Overlay(String name) {
            this.name = name;
        }

        public final String getName() {
            return name;
        }
    }
}
