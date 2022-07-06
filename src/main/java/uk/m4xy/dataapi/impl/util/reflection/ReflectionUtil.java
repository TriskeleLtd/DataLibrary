package uk.m4xy.dataapi.impl.util.reflection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtil {
    public static List<Field> getFieldsUpTo(@NotNull Class<?> source, @Nullable Class<?> exclusiveParent) {
        List<Field> currentClassFields = Arrays.asList(source.getDeclaredFields());
        Class<?> parentClass = source.getSuperclass();

        if (parentClass == null || parentClass.equals(exclusiveParent)) {
            return currentClassFields;
        }

        currentClassFields.addAll(getFieldsUpTo(parentClass, exclusiveParent));
        return currentClassFields;
    }
}
