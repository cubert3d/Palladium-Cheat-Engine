package me.cubert3d.palladium.event.mixin;

public interface MixinCaster<T> {
    default T self() {
        return (T) this;
    }
}
