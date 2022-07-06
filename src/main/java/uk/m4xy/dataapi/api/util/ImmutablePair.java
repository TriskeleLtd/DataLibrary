package uk.m4xy.dataapi.api.util;

import org.jetbrains.annotations.NotNull;

public class ImmutablePair<L, R> {
    @NotNull
    private final L left;
    @NotNull
    private final R right;

    public ImmutablePair(@NotNull L left, @NotNull R right) {
        this.left = left;
        this.right = right;
    }

    public @NotNull L getLeft() {
        return left;
    }

    public @NotNull R getRight() {
        return right;
    }
}
