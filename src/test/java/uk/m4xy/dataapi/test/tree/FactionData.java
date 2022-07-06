package uk.m4xy.dataapi.test.tree;

import uk.m4xy.dataapi.api.data.reference.DataReference;
import uk.m4xy.dataapi.api.data.reflect.ReflectedDataObject;
import uk.m4xy.dataapi.api.data.reflect.annotation.Id;
import uk.m4xy.dataapi.api.data.reflect.annotation.Key;
import uk.m4xy.dataapi.impl.data.reflection.element.constructor.sql.SQLElement;
import uk.m4xy.dataapi.impl.data.reflection.element.constructor.sql.SQLReference;

public class FactionData extends ReflectedDataObject<FactionDataType, String, FactionData, FactionDataType.LoadLevels> {

    @Key
    @SQLElement
    String name;

    @Id
    @SQLElement
    Long id;

    @SQLReference(column = "test", dataTypeClass = FactionDataType.class)
    DataReference<FactionData> factionData;

    int x, y, z;

    public FactionData(FactionDataType typeInstance) {
        super(typeInstance);
    }
}
