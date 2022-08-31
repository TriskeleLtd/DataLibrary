package uk.m4xy.dataapi.impl.data.sql.field;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;
import uk.m4xy.dataapi.api.data.element.type.ImmutableDataField;

import java.util.function.Supplier;

public class ImmutablePrimitiveDataField<E> implements ImmutableDataField<E> {

    private final Supplier<E> getter;

    public ImmutablePrimitiveDataField(@NotNull Supplier<E> getter) {
        this.getter = getter;
    }

    @NotNull
    public static <E> ImmutablePrimitiveDataField<E> of(@NotNull Supplier<E> getter) {
        return new ImmutablePrimitiveDataField<>(getter);
    }

    @NotNull
    public static <E> ImmutablePrimitiveDataField<E> of(@Nullable E value) {
        return new ImmutablePrimitiveDataField<>(() -> value);
    }

    @NotNull
    @Override
    public E get() throws DataNotLoadedException {
        final E e = this.getter.get();
        if (e == null) {
            throw new DataNotLoadedException();
        }

        return e;
    }
}
