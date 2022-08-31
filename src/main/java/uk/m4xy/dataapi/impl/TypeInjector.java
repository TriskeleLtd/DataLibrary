package uk.m4xy.dataapi.impl;

import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;

public class TypeInjector {

    public static @NotNull Injector getInjector() {
        return null; // TODO;
    }

    @NotNull
    public static <T> T get(Class<T> clazz) {
        return getInjector().getInstance(clazz);
    }

}
