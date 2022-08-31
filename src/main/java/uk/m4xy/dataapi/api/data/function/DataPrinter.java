package uk.m4xy.dataapi.api.data.function;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;

public interface DataPrinter<T extends DataType<T, ?, D>, D extends Data<T, ?, D>> {
    @NotNull String[] getPrintData(@NotNull D data);
}
