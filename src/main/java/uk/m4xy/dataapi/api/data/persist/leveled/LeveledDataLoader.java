package uk.m4xy.dataapi.api.data.persist.leveled;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.persist.DataLoader;

public interface LeveledDataLoader<T extends LeveledDataType<T, K, D, L>, K, D extends Data<T, K, D>, L extends Enum<L> & LoadLevel> extends DataLoader<T, K, D> {

    @Nullable
    D loadData(@NotNull K key, @NotNull LoadLevel loadLevel);
    @Nullable
    D loadData(long id, @NotNull LoadLevel loadLevel);

    @Nullable
    default D loadDataFromId(long id, @NotNull LoadLevel loadLevel) {
        return this.loadData(id, loadLevel);
    }

}
