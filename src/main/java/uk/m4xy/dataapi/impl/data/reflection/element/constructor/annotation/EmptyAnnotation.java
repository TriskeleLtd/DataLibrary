package uk.m4xy.dataapi.impl.data.reflection.element.constructor.annotation;

import java.lang.annotation.Annotation;

public class EmptyAnnotation implements Annotation {
    @Override
    public Class<? extends Annotation> annotationType() {
        return EmptyAnnotation.class;
    }
}
