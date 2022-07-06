package uk.m4xy.dataapi.api.type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public class GsonStringTypeConverter<T> implements StringTypeConverter<T> {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    private final Type type;

    public GsonStringTypeConverter(Type type) {
        this.type = type;
    }
    @Override
    public @NotNull Type getDataType() {
        return type;
    }

    @Override
    public @NotNull T deserialize(@NotNull String string) {
        return GSON.fromJson(string, type);
    }

    @Override
    public @NotNull String serialize(@NotNull T key) {
        return GSON.toJson(key);
    }
}
