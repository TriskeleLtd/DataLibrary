package uk.m4xy.dataapi.api.data.reference;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.reference.exception.ReferenceNotLoadedException;

public interface Reference<R extends Referenceable> {

    @NotNull R get() throws ReferenceNotLoadedException;

}
