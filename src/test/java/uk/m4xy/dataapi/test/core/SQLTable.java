package uk.m4xy.dataapi.test.core;

import uk.m4xy.dataapi.impl.data.sql.EnvironmentSQLDatabasePath;
import uk.m4xy.dataapi.impl.data.sql.SQLDatabasePath;

public @interface SQLTable {

    String value(); // Table name

    Class<? extends SQLDatabasePath> databasePath() default EnvironmentSQLDatabasePath.class;

}
