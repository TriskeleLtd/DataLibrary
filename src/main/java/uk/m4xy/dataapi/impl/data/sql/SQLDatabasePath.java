package uk.m4xy.dataapi.impl.data.sql;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface SQLDatabasePath {

    @NotNull
    String getHost();

    @Range(from = 0, to = 65535) int getPort();

    @NotNull
    String getUser();

    @NotNull
    String getPassword();

    @NotNull
    String getDatabase();

}
