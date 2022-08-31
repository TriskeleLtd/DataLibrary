package uk.m4xy.dataapi.api.data.annotation.type;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Documented
public @interface TypeConstructorAnnotation {
    Class<? extends TypeConstructor<? extends Annotation>> value();

}
