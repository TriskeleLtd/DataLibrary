package uk.m4xy.dataapi.test.core;

public @interface SQLKey {

    String value() default ""; // Infers based on field name


}
