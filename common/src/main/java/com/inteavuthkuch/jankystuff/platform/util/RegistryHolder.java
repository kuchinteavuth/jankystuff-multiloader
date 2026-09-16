package com.inteavuthkuch.jankystuff.platform.util;

import net.minecraft.resources.Identifier;

public interface RegistryHolder<T> {
    T get();
    Identifier id();

    static <T> RegistryHolder<T> of(Identifier id, T data) {
        return new RegistryHolder<T>() {
            @Override
            public T get() {
                return data;
            }

            @Override
            public Identifier id() {
                return id;
            }
        };
    }
}
