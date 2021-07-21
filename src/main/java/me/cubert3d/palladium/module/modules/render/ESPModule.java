package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.event.callback.EntityRenderCallback;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.module.setting.list.EntityListSetting;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

@ClassInfo(
        authors = "cubert3d",
        date = "6/23/2021",
        type = ClassType.MODULE
)

public final class ESPModule extends ToggleModule {

    private final EntityListSetting entities;

    public ESPModule() {
        super("ESP", "Renders a box around entities that can be seen through walls.");
        this.entities = new EntityListSetting("Entities");
        this.addSetting(entities);
    }

    @Override
    public void onLoad() {
        EntityRenderCallback.EVENT.register(entity -> isEnabled() && shouldDrawESP(entity));
    }

    private boolean shouldDrawESP(@NotNull Entity entity) {
        return !entity.equals(MinecraftClient.getInstance().player) && entities.getList().contains(entity.getType());
    }
}
