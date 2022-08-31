package uk.m4xy.dataapi.api.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface GetterSetter<O, E> {

    @Nullable E get(@NotNull O object);

    void set(@NotNull O object, @Nullable E e);


}
