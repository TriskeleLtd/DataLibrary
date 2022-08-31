package dev.triskele.common.environment;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface EnvironmentProvider {

    @NotNull <T> Optional<T> getEnvironment(final @NotNull Class<T> environmentClass);

}
