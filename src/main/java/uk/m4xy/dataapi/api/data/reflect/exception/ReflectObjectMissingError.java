package uk.m4xy.dataapi.api.data.reflect.exception;

import uk.m4xy.dataapi.api.data.reflect.FieldPointer;
import uk.m4xy.dataapi.api.data.reflect.GetterSetterMethodPointer;
import uk.m4xy.dataapi.api.data.reflect.ReflectedDataObject;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.ReflectiveGetterSetterPointer;

public class ReflectObjectMissingError extends AssertionError {

    public static <E, O extends ReflectedDataObject<?, ?, O, ?>> ReflectObjectMissingError from(Class<O> dataClass, ReflectiveGetterSetterPointer<O, E> pointer) {
        if (pointer instanceof FieldPointer) {
            return new ReflectObjectMissingError(dataClass, ((FieldPointer<O, E>) pointer));
        } else if (pointer instanceof GetterSetterMethodPointer) {
            return new ReflectObjectMissingError(dataClass, ((GetterSetterMethodPointer<O, E>) pointer));
        }
        return new ReflectObjectMissingError(dataClass, pointer);
    }

    public <O extends ReflectedDataObject<?, ?, O, ?>> ReflectObjectMissingError(Class<O> dataClass, ReflectiveGetterSetterPointer<O, ?> pointer) {
        super(dataClass.getName() + " is missing a feature required by " + pointer);
    }

    public <O extends ReflectedDataObject<?, ?, O, ?>> ReflectObjectMissingError(Class<O> dataClass, FieldPointer<O, ?> missing) {
        super(dataClass.getName() + " is missing the field " + missing.fieldName());
    }

    public <O extends ReflectedDataObject<?, ?, O, ?>> ReflectObjectMissingError(Class<O> dataClass, GetterSetterMethodPointer<O, ?> missing) {
        super(dataClass.getName() + " is missing one of the methods: " + missing.getterMethodName() + " or " + missing.setterMethodName());
    }
}
