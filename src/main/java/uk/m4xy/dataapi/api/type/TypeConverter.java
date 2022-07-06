package uk.m4xy.dataapi.api.type;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.impl.type.LambdaTypeConverter;

import java.lang.reflect.Type;
import java.util.function.Function;

public interface TypeConverter<D, S> {
    @NotNull
    static <D> TypeConverter<D, D> self(Type clazz) {
        return new LambdaTypeConverter<>(clazz, clazz, Function.identity(), Function.identity());
    }

    @NotNull Type getSerializedDataType();

    @NotNull Type getDataType();

    @NotNull S serialize(@NotNull D data);

    @NotNull D deserialize(@NotNull S serializedData);

}
