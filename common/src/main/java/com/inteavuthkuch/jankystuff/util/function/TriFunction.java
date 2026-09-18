package com.inteavuthkuch.jankystuff.util.function;

@FunctionalInterface
public interface TriFunction<Q, W, E, R> {
    R apply(Q q, W w, E e);
}
