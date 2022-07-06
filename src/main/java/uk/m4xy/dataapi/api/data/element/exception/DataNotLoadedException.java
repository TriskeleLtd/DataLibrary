package uk.m4xy.dataapi.api.data.element.exception;

public class DataNotLoadedException extends RuntimeException {

    public DataNotLoadedException() {
    }

    public DataNotLoadedException(String message) {
        super(message);
    }
}
