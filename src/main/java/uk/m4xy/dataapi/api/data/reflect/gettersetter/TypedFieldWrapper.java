package uk.m4xy.dataapi.api.data.reflect.gettersetter;

import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public final class TypedFieldWrapper<O, E> implements ReflectiveGetterSetter<O, E> {
    @NotNull
    private final Field field;
    @NotNull
    private final Type type;

    public TypedFieldWrapper(@NotNull TypeToken<E> type, @NotNull Field field) {
        this.type = type.getType();
        this.field = field;
    }

    public TypedFieldWrapper(@NotNull Class<E> type, @NotNull Field field) {
        this.type = type;
        this.field = field;
    }

    public TypedFieldWrapper(@NotNull Type type, @NotNull Field field) {
        this.type = type;
        this.field = field;
    }

    public TypedFieldWrapper(@NotNull Field field) {
        this.type = field.getType();
        this.field = field;
    }

    public void set(@NotNull O object, E type) {
        this.field.setAccessible(true);

        Field modifiersField;
        try {
            modifiersField = Field.class.getDeclaredField("modifiers");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        modifiersField.setAccessible(true);

        try {
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            this.field.set(object, type);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @SuppressWarnings("unchecked")
    @Nullable
    public E get(@NotNull O object) {
        try {
            return (E) this.field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public Type getType() {
        return type;
    }

    @NotNull
    public Field getField() {
        return field;
    }
}
