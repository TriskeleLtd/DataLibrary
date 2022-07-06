package uk.m4xy.dataapi.api.util.service;

import java.util.function.Supplier;

@FunctionalInterface
public interface ServiceSupplier<S> extends Supplier<S> {
    default boolean isLoaded() {
        return true;
    }

}
