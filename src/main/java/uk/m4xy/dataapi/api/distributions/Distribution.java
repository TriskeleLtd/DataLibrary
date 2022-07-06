package uk.m4xy.dataapi.api.distributions;

import org.jetbrains.annotations.NotNull;

public interface Distribution {

    static Distribution getInstance() {
        // TODO;
        return null;
    }
    @NotNull String getUniqueIdentifier();

}
