package uk.m4xy.dataapi.api.data.tree;

import org.jetbrains.annotations.NotNull;

public interface LoadLevel{
    int getLevel();

    default boolean isStrongerThan(@NotNull LoadLevel o) {
        // Highest first.
        return o.getLevel() < this.getLevel();
    }
}
