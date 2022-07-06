package uk.m4xy.dataapi.api.data.tree;

import uk.m4xy.dataapi.api.data.element.DataElement;

public class $EnumDataTree<E extends Enum<E>> {
    private final Class<E> levels;

    public $EnumDataTree(Class<E> levels) {
        this.levels = levels;
    }

    public $EnumDataTree<E> add(E key, DataElement<?, ?, ?>... dataElements) {
        return this;
    }
}
