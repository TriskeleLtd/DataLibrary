package uk.m4xy.dataapi.test.tree;

import uk.m4xy.dataapi.api.data.persist.leveled.LoadLevel;
import uk.m4xy.dataapi.impl.data.reflection.ReflectiveDataType;

public class FactionDataType extends ReflectiveDataType<FactionDataType, String, FactionData, FactionDataType.LoadLevels> {

    public FactionDataType() {
        super(null, null, null);

    }

    @Override
    protected void registerOtherElements() {
    }


    public enum LoadLevels implements LoadLevel {
        ALWAYS,
        PLAYER_ONLINE,
        MODIFY;

        @Override
        public int getLevel() {
            return this.ordinal();
        }
    }

}