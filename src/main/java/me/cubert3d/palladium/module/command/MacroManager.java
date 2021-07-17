package me.cubert3d.palladium.module.command;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "7/16/2021",
        type = ClassType.MANAGER
)

public final class MacroManager {

    private final Map<String, Macro> macros;

    public MacroManager() {
        this.macros = new HashMap<>();
    }

    public final Optional<Macro> getMacro(@NotNull String name) {
        final String id = name.trim().toLowerCase().replaceAll(" ", "_");
        return Optional.ofNullable(macros.get(id));
    }

    public final @NotNull Macro createMacro(String name) {
        Macro macro = new Macro(name);
        macros.put(macro.getID(), macro);
        return macro;
    }

    public final Optional<Macro> removeMacro(String name) {
        final String id = name.trim().toLowerCase().replaceAll(" ", "_");
        return Optional.ofNullable(macros.remove(id));
    }

    @Contract(" -> new")
    public final @NotNull ArrayList<Macro> getMacroList() {
        return new ArrayList<>(macros.values());
    }
}
