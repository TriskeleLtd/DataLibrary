package uk.m4xy.dataapi.api.data.element.type;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;
import uk.m4xy.dataapi.api.util.service.ServiceSupplier;

public class ServiceDataField<S> implements ImmutableDataField<S> {
    private ServiceSupplier<S> serviceSupplier;

    @Override
    public @NotNull S get() {
        if (!this.serviceSupplier.isLoaded()) throw new DataNotLoadedException();

        return this.serviceSupplier.get();
    }
}
