package uk.m4xy.dataapi.impl.data.sql;

import dev.triskele.common.environment.annotation.EnvironmentPath;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface EnvironmentSQLDatabasePath extends SQLDatabasePath {

    @NotNull
    @Override
    @EnvironmentPath("SQL_HOST")
    String getHost();

    @Override
    @EnvironmentPath("SQL_PORT")
    @Range(from = 0, to = 65535) int getPort();

    @NotNull
    @Override
    @EnvironmentPath("SQL_USER")
    String getUser();

    @NotNull
    @Override
    @EnvironmentPath("SQL_PASSWORD")
    String getPassword();

    @NotNull
    @Override
    @EnvironmentPath("SQL_DATABASE")
    String getDatabase();
}
