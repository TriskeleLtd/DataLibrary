package uk.m4xy.dataapi.api.data.element.type;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListDataField<E> implements ImmutableDataField<List<E>> {
    private final Type type;
    private final List<E> list;

    public ListDataField(Type type) {
        this.type = type;

        this.list = new ArrayList<>();
    }

    public ListDataField(Type type, List<E> initialValues) {
        this.type = type;

        this.list = new ArrayList<>(initialValues);
    }

    @Override
    public @NotNull List<E> get() {
        return this.list;
    }
}
