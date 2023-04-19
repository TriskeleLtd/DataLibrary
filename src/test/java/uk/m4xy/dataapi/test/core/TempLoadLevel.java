package uk.m4xy.dataapi.test.core;

import uk.m4xy.dataapi.api.data.persist.leveled.annotation.LoadLevelAnnotation;

@LoadLevelAnnotation(TempLoads.class)
public @interface TempLoadLevel {
    TempLoads value();
    long millis() default 10 * 1000 * 60L;
}
