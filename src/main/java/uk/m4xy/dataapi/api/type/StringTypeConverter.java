package uk.m4xy.dataapi.api.type;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface StringTypeConverter<T> extends TypeConverter<T, String> {
    Map<Type, StringTypeConverter<?>> SERIALIZERS = new ConcurrentHashMap<>();

    static void registerStringSerializer(StringTypeConverter<?> serializer) {
        SERIALIZERS.put(serializer.getDataType(), serializer);
    }

    @NotNull
    @SuppressWarnings("unchecked")
    static <T> StringTypeConverter<T> getStringSerializer(Class<T> type) {
        return (StringTypeConverter<T>) SERIALIZERS.computeIfAbsent(type, t -> new GsonStringTypeConverter<>(type));
    }

    @NotNull
    @SuppressWarnings("unchecked")
    static <O> String serializeObject(O object) {
        return ((StringTypeConverter<O>) getStringSerializer(object.getClass())).serialize(object);
    }

    @NotNull
    static <O> O deserializeObject(String object, Class<O> type) {
        return getStringSerializer(type).deserialize(object);
    }

    @NotNull Type getDataType();

    default @NotNull Class<String> getSerializedDataType() {
        return String.class;
    }

}
