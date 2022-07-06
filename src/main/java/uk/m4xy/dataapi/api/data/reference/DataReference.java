package uk.m4xy.dataapi.api.data.reference;

import uk.m4xy.dataapi.api.data.Data;

public interface DataReference<D extends Data<?, ?, D>> extends Reference<D> {

    long getId();

}
