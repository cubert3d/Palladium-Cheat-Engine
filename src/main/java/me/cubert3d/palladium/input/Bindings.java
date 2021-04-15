package me.cubert3d.palladium.input;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class Bindings {

    private static final Set<PalladiumKeyBinding> bindings = new HashSet<>();

    private Bindings() {}

    public static void addBinding(PalladiumKeyBinding binding) {
        bindings.add(binding);
    }

    public static Optional<PalladiumKeyBinding> getBindingFromCode(int code) {
        for (PalladiumKeyBinding binding : bindings) {
            if (binding.getKeyCode() == code) {
                return Optional.of(binding);
            }
        }
        return Optional.empty();
    }
}
