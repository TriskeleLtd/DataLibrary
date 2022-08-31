package uk.m4xy.dataapi.impl.distributions;

import dev.triskele.common.environment.annotation.EnvironmentPath;
import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.distributions.Distribution;

public interface EnvironmentDistribution extends Distribution {
    @NotNull
    @Override
    @EnvironmentPath("SERVER_IDENTIFIER")
    String getUniqueIdentifier();

}
