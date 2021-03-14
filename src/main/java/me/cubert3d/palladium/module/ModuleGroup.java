package me.cubert3d.palladium.module;

import me.cubert3d.palladium.util.Named;
import me.cubert3d.palladium.util.annotation.ClassDescription;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
    Like a module category, but the user will be able to define their own.
    Inspired by Nodus.
 */

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/6/2021",
        status = "complete"
)

public final class ModuleGroup implements Named {

    private final String name;
    private final Set<Module> modules = new HashSet<>();

    public ModuleGroup(String name) {
        this.name = name;
    }

    public ModuleGroup(String name, Module... modules) {
        this.name = name;
        this.modules.addAll(Arrays.asList(modules));
    }

    @Override
    public String getName() {
        return name;
    }

    public final Set<Module> getModules() {
        return modules;
    }

    public final void addModule(Module module) {
        modules.add(module);
    }



    // Pre-defined module groups

    public static final ModuleGroup DEFAULT_MOVEMENT = new ModuleGroup("Movement");

    public static final ModuleGroup DEFAULT_PLAYER = new ModuleGroup("Player");

    public static final ModuleGroup DEFAULT_RENDER = new ModuleGroup("Render");
}
