package uk.m4xy.dataapi.api.data.persist;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.element.DataElement;

public interface DataLoader<T extends DataType<T, K, D>, K, D extends Data<T, K, D>> {

    @Nullable D loadData(@NotNull K key, @NotNull DataElement<T, D, ?>... elementsToLoad);
    @Nullable D loadData(@NotNull K key);

    @Nullable D loadData(long id, @NotNull DataElement<T, D, ?>... elementsToLoad);

    @Nullable D loadData(long id);

    @Nullable
    default D loadDataFromId(long id) {
        return this.loadData(id);
    }

    @Nullable
    default D loadDataFromId(long id, @NotNull DataElement<T, D, ?>... elementsToLoad) {
        return this.loadData(id, elementsToLoad);
    }

}

