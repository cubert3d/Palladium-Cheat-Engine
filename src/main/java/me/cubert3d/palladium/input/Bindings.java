package me.cubert3d.palladium.input;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class Bindings {

    private static final Set<PlKeyBinding> bindings = new HashSet<>();

    private Bindings() {}

    public static void addBinding(PlKeyBinding binding) {
        bindings.add(binding);
    }

    public static Set<PlKeyBinding> getBindings() {
        return bindings;
    }

    public static Optional<PlKeyBinding> getBindingFromCode(int code) {
        for (PlKeyBinding binding : bindings) {
            if (binding.getKeyCode() == code) {
                return Optional.of(binding);
            }
        }
        return Optional.empty();
    }
}
