package uk.m4xy.dataapi.api.data.persist.leveled;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.persist.DataSaver;

public interface LeveledDataSaver<T extends LeveledDataType<T, K, D, L>, K, D extends Data<T, K, D>, L extends Enum<L> & LoadLevel> extends DataSaver<T, K, D> {

    void saveData(@NotNull D data, @NotNull LoadLevel loadLevel);

}
