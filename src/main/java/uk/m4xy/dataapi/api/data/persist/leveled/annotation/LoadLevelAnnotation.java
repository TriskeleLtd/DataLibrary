package uk.m4xy.dataapi.api.data.persist.leveled.annotation;

/**
 * Annotate annotations that denote LoadLevels in classes.
 *
 * e.g
 * <pre>
 * {@code
 * enum Level implements LoadLevel {
 *     LOADED_WHEN_DEMANDED,
 *     CACHED_WHEN_DEMANDED,
 *     LOADED_WHEN_ACTIVE,
 *     ALWAYS_LOADED;
 * }
 *
 * @LoadLevelAnnotation(Level.class)
 * @interface LoadedWhen {
 *     Level value();
 * }
 * }
 *</pre>
 *
 */
public @interface LoadLevelAnnotation {

    Class<? extends Enum<?>> value();

    int defaultLoadIndex() default 0;

}
