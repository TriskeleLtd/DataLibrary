package uk.m4xy.dataapi.api.data.annotation.type;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.DataTypeInitializer;

import java.lang.annotation.Annotation;

@FunctionalInterface
public interface TypeConstructor<A extends Annotation> {
    <T extends DataType<T, K, D>, K, D extends Data<T, K, D>>
    void apply(
            @NotNull A annotation,
            @NotNull DataTypeInitializer<T, K, D> initializer
    );
}
