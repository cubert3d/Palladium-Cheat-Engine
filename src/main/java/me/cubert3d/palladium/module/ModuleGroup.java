package me.cubert3d.palladium.module;

import me.cubert3d.palladium.module.modules.Module;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@ClassInfo(
        description = "Contains a list of modules grouped together; unlike pre-defined categories, players can create their own groups. Inspired by Nodus.",
        authors = "cubert3d",
        date = "3/6/2021",
        type = ClassType.MISC
)

public final class ModuleGroup {

    private String name;
    private String id;
    private final ArrayList<Module> modules;

    ModuleGroup(String name, ArrayList<Module> modules) {
        this.setName(name);
        this.modules = modules;
    }

    public ModuleGroup(String name) {
        this.setName(name);
        this.modules = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ModuleGroup) {
            return this.getID().equals(((ModuleGroup) obj).getID());
        }
        return false;
    }

    public final String getName() {
        return name;
    }

    public final void setName(@NotNull String name) {
        this.name = name.trim();
        this.id = name.trim().toLowerCase().replace(" ", "_");
    }

    final String getID() {
        return id;
    }

    public final ArrayList<Module> getModules() {
        return modules;
    }

    public final boolean addModule(@NotNull Module module) {
        if (module.getType().equals(ModuleType.TOGGLE)) {
            return modules.add(module);
        }
        else {
            return false;
        }
    }
}
