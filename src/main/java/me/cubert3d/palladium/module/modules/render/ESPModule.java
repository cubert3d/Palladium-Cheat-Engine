package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.event.callback.EntityRenderCallback;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.module.setting.list.EntityListSetting;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.NotNull;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "6/23/2021",
        status = "in-progress"
)

public final class ESPModule extends ToggleModule {

    private final EntityListSetting entities;

    public ESPModule() {
        super("ESP", "Renders a box around entities that can be seen through walls.", ModuleDevStatus.AVAILABLE);
        this.entities = new EntityListSetting("Entities");
        this.addSetting(entities);
    }

    @Override
    public void onLoad() {
        EntityRenderCallback.EVENT.register(entity -> {
            if (isEnabled() && shouldDrawESP(entity)) {
                return ActionResult.FAIL;
            }
            else {
                return ActionResult.PASS;
            }
        });
    }

    private boolean shouldDrawESP(@NotNull Entity entity) {
        return !entity.equals(MinecraftClient.getInstance().player) && entities.getList().contains(entity.getType());
    }
}
