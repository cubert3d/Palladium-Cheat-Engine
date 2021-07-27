package me.cubert3d.palladium.module;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.module.modules.Module;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.exception.ModuleNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.stream.Collectors;

@ClassInfo(
        description = "Manages all module groups, whether the default groups, or groups created by the player.",
        authors = "cubert3d",
        date = "7/5/2021",
        type = ClassType.MANAGER
)

public final class ModuleGroupManager {

    private final Map<String, ModuleGroup> groups;
    private final ModuleManager moduleManager;

    public ModuleGroupManager(ModuleManager moduleManager) {
        this.groups = new LinkedHashMap<>();
        this.moduleManager = moduleManager;
    }

    public void initialize() {
        Palladium.getLogger().info("Loading default module groups...");
        loadDefaultGroups();
        Palladium.getLogger().info("Done loading default module groups!");
    }

    void loadDefaultGroups() {
        try {

            Map<String, ModuleGroup> defaultGroups = new HashMap<>();
            LinkedHashSet<Module> modules = moduleManager.getModules().stream()
                    .filter(module -> module.getType().equals(ModuleType.TOGGLE))
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            for (Module module : modules) {

                String packageName = module.getClass().getPackage().getName();
                packageName = packageName.substring(packageName.lastIndexOf(".") + 1).toUpperCase();

                String finalPackageName = packageName;
                defaultGroups.compute(packageName, ((s, moduleGroup) -> {
                    if (moduleGroup != null) {
                        moduleGroup.addModule(module);
                        return moduleGroup;
                    }
                    else {
                        ModuleGroup newModuleGroup = new ModuleGroup(finalPackageName);
                        newModuleGroup.addModule(module);
                        return newModuleGroup;
                    }
                }));

            }

            for (ModuleGroup moduleGroup : defaultGroups.values()) {
                addModuleGroup(moduleGroup);
            }
        }
        catch (ModuleNotFoundException e) {
            e.printStackTrace();
        }
    }

    public final @NotNull ModuleGroup newModuleGroup(@NotNull String name) {
        ModuleGroup group = new ModuleGroup(name.trim(), new ArrayList<>());
        addModuleGroup(group);
        return group;
    }

    @SafeVarargs
    public final @NotNull ModuleGroup newModuleGroup(String name, Class<? extends Module> @NotNull ... moduleClasses) throws ModuleNotFoundException {
        ModuleGroup group;
        ArrayList<Module> modules = new ArrayList<>();

        for (Class<? extends Module> moduleClass : moduleClasses) {
            try {
                modules.add(moduleManager.getModuleByClass(moduleClass));
            }
            catch (ModuleNotFoundException e) {
                e.printStackTrace();
            }
        }
        group = new ModuleGroup(name.trim(), modules);
        return group;
    }

    public ModuleGroup getGroup(@NotNull String id) {
        return groups.get(id.trim().toLowerCase());
    }

    public Map<String, ModuleGroup> getGroups() {
        return groups;
    }

    private void addModuleGroup(@NotNull ModuleGroup group) {
        Palladium.getLogger().info("Added module group \"" + group.getID() + "\"");
        groups.put(group.getID(), group);
    }
}
