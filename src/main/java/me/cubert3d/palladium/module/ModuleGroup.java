package me.cubert3d.palladium.module;

import me.cubert3d.palladium.util.Named;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
    Like a module category, but the user will be able to define their own.
    Inspired by Nodus.

    Written by cubert3d on 3/6/2021
 */

public final class ModuleGroup implements Named {

    private final String name;
    private final Set<AbstractModule> modules = new HashSet<>();

    public ModuleGroup(String name) {
        this.name = name;
    }

    public ModuleGroup(String name, AbstractModule... modules) {
        this.name = name;
        this.modules.addAll(Arrays.asList(modules));
    }

    @Override
    public String getName() {
        return name;
    }

    public final Set<AbstractModule> getModules() {
        return modules;
    }

    public final void addModule(AbstractModule module) {
        modules.add(module);
    }



    // Pre-defined module groups

    public static final ModuleGroup DEFAULT_MOVEMENT = new ModuleGroup("Movement");

    public static final ModuleGroup DEFAULT_PLAYER = new ModuleGroup("Player");

    public static final ModuleGroup DEFAULT_RENDER = new ModuleGroup("Render");
}
