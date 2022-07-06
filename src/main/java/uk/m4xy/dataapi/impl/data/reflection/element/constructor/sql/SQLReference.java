package uk.m4xy.dataapi.impl.data.reflection.element.constructor.sql;

import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.impl.data.reflection.element.constructor.annotation.ReflectiveElementTypeDefinition;

@ReflectiveElementTypeDefinition(SQLReferenceConstructor.class)
public @interface SQLReference {

    String column();

    Class<? extends DataType<?, ?, ?>> dataTypeClass();
}
