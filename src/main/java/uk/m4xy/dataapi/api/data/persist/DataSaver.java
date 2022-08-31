package uk.m4xy.dataapi.api.data.persist;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.element.DataElement;

public interface DataSaver<T extends DataType<T, K, D>, K, D extends Data<T, K, D>> {

    void saveData(@NotNull D data, @NotNull DataElement<T, D, ?>... elementsToSave);

    void saveData(@NotNull D data);

}

