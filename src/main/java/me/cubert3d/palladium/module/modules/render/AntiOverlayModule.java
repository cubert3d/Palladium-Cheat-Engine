package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.event.callback.OverlayCallback;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.module.setting.single.BooleanSetting;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import me.cubert3d.palladium.util.annotation.Named;
import net.minecraft.util.ActionResult;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/10/2021"
)

public final class AntiOverlayModule extends ToggleModule {

    public enum Overlay implements Named {
        PUMPKIN("Pumpkin"),
        PORTAL("Portal"),
        NAUSEA("Nausea"),
        BLINDNESS("Blindness");

        private final String name;

        Overlay(String name) {
            this.name = name;
        }

        @Override
        public final String getName() {
            return name;
        }
    }

    public AntiOverlayModule() {
        super("AntiOverlay", "Removes obtrusive overlays.", ModuleDevStatus.AVAILABLE);
        this.addSetting(new BooleanSetting("Pumpkin", true));
        this.addSetting(new BooleanSetting("Portal", true));
        this.addSetting(new BooleanSetting("Nausea", true));
        this.addSetting(new BooleanSetting("Blindness", true));
    }

    @Override
    public void onLoad() {
        OverlayCallback.EVENT.register(overlay -> {
            if (this.isEnabled() && this.getSetting(overlay.getName()).asBooleanSetting().getValue()) {
                return ActionResult.FAIL;
            }
            else {
                return ActionResult.PASS;
            }
        });
    }
}
