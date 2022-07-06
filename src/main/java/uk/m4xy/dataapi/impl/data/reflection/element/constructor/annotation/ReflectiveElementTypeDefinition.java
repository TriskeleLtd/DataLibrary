package uk.m4xy.dataapi.impl.data.reflection.element.constructor.annotation;

import uk.m4xy.dataapi.impl.data.reflection.element.constructor.ReflectiveElementConstructor;

import java.lang.annotation.*;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ReflectiveElementTypeDefinition {
    Class<? extends ReflectiveElementConstructor> value();

}
