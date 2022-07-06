package uk.m4xy.dataapi.api.data.element.type;

public sealed interface DataField permits ImmutableDataField, ModifiableDataField {

    boolean isSettable();


}
