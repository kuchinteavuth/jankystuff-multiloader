package com.inteavuthkuch.jankystuff.platform.util;

import net.minecraft.resources.Identifier;

public interface IRegistryHolder<T> {
    T get();
    Identifier id();

    static <T> IRegistryHolder<T> of(Identifier id, T data) {
        return new IRegistryHolder<T>() {
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
