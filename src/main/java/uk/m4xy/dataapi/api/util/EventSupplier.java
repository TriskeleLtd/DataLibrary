package uk.m4xy.dataapi.api.util;

import java.util.function.Consumer;

public abstract class EventSupplier<T> {
    private Consumer<T> listener;

    public EventSupplier(Consumer<T> listener) {
        this.listener = listener;
    }

    public void supply(T t) {
        if (listener == null) {
            return;
        }

        this.listener.accept(t);
    }

    public void setListener(Consumer<T> listener) {
        this.listener = listener;
    }

}
