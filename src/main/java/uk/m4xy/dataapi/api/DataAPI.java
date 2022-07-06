package uk.m4xy.dataapi.api;

import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.util.service.ServiceSupplier;

import javax.management.ServiceNotFoundException;

public interface DataAPI {

    <T extends DataType<T, ?, ?>> void registerDataType(Class<T> type, DataType<T, ?, ?> dataType);


    // Services
    <S> void registerService(Class<S> type, ServiceSupplier<S> provider);

    <S> S getService(Class<S> type) throws ServiceNotFoundException;



}
