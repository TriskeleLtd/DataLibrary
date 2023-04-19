package uk.m4xy.dataapi.test.core;

public @interface SQLColumnReference {

    Class<?> type();

    String column();

}
