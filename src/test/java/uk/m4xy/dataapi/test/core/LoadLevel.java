package uk.m4xy.dataapi.test.core;

import uk.m4xy.dataapi.api.data.persist.leveled.annotation.LoadLevelAnnotation;
import uk.m4xy.dataapi.test.Level;

@LoadLevelAnnotation(Level.class)
public @interface LoadLevel {
    Level value() default Level.CACHE_ON_REQUEST;
}
