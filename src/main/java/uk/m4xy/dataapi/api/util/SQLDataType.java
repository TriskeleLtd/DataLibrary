package uk.m4xy.dataapi.api.util;

import java.sql.SQLType;

public enum SQLDataType implements SQLType {

    ;

    private final int type;

    SQLDataType(int type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getVendor() {
        return null;
    }

    @Override
    public Integer getVendorTypeNumber() {
        return null;
    }

    public static SQLDataType valueOf(int type) {
        for (SQLDataType sqlType : SQLDataType.class.getEnumConstants()) {
            if (type == sqlType.type)
                return sqlType;
        }
        throw new IllegalArgumentException("Type:" + type + " is not a valid "
                + "Types.java value.");
    }

}
