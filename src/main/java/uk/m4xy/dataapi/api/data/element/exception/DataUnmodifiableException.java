package uk.m4xy.dataapi.api.data.element.exception;

import uk.m4xy.dataapi.api.data.element.DataElement;

public class DataUnmodifiableException extends IllegalArgumentException {

    public DataUnmodifiableException(DataElement<?, ?, ?> element, String message) {
        super(message);
        //TODO
    }

    public DataUnmodifiableException(DataElement<?, ?, ?> element) {
        //TODO
    }
}
