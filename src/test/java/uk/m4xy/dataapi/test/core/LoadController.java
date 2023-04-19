package uk.m4xy.dataapi.test.core;

import uk.m4xy.dataapi.test.tree.FactionData;
import uk.m4xy.dataapi.test.tree.FactionDataType;

class OfflineFaction {

    public OfflineFaction() {
        FactionDataType userDataType = new FactionDataType();
        FactionData user = new FactionData(userDataType);


        final FactionDataType.LoadLevels loadLevel = user.getLoadLevel();
        if (loadLevel.isStrongerThan(FactionDataType.LoadLevels.PLAYER_ONLINE)) {

        }

    }

}