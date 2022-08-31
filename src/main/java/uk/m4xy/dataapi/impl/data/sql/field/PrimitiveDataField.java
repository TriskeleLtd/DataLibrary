package uk.m4xy.dataapi.impl.data.sql.field;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;
import uk.m4xy.dataapi.api.data.element.type.ModifiableDataField;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class PrimitiveDataField<E> implements ModifiableDataField<E> {
    private final Supplier<E> getter;
    private final Consumer<E> setter;
    public PrimitiveDataField(@NotNull Supplier<E> getter, @NotNull Consumer<E> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public void set(@Nullable E value) {
        this.setter.accept(value);
    }

    @Override
    public boolean isSettable() {
        return true;
    }

    @NotNull
    @Override
    public E get() throws DataNotLoadedException {
        return this.getter.get();
    }

    @NotNull
    public static <E> PrimitiveDataField<E> of(@NotNull Supplier<E> getter, @NotNull Consumer<E> setter) {
        return new PrimitiveDataField<>(getter, setter);
    }
}
