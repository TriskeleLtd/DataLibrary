package uk.m4xy.dataapi.impl.type;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.type.TypeConverter;

import java.lang.reflect.Type;
import java.util.function.Function;

public class LambdaTypeConverter<D, S> implements TypeConverter<D, S> {

    private final Type dataClass;
    private final Type serializedDataClass;

    private final Function<D, S> serializer;
    private final Function<S, D> deserializer;

    public LambdaTypeConverter(Type dataClass, Type serializedDataClass, Function<D, S> serializer, Function<S, D> deserializer) {
        this.dataClass = dataClass;
        this.serializedDataClass = serializedDataClass;
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    @Override
    public @NotNull Type getSerializedDataType() {
        return this.serializedDataClass;
    }

    @Override
    public @NotNull Type getDataType() {
        return this.dataClass;
    }

    @Override
    public @NotNull S serialize(@NotNull D data) {
        return this.serializer.apply(data);
    }

    @Override
    public @NotNull D deserialize(@NotNull S serializedData) {
        return this.deserializer.apply(serializedData);
    }
}
