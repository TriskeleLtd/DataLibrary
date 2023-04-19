package uk.m4xy.dataapi.api.data.reflect;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.element.DataElement;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;
import uk.m4xy.dataapi.api.data.persist.leveled.LoadLevel;
import uk.m4xy.dataapi.impl.data.reflection.ReflectiveDataType;

import java.util.ArrayList;

public abstract class ReflectedDataObject<
        T extends ReflectiveDataType<T, K, O, L>,
        K,
        O extends ReflectedDataObject<T, K, O, L>,
        L extends Enum<L> & LoadLevel>
        implements Data<T, K, O> {
    private final @NotNull T typeInstance;

    private L loadLevel;

    public ReflectedDataObject(@NotNull T typeInstance) {
        this.typeInstance = typeInstance;
    }


    @SuppressWarnings("unchecked")
    public void commit() {
        this.getType().commit((O) this);
    }

    @SuppressWarnings("unchecked")
    public void commit(@NotNull DataElement<T, O, ?>... dataElements) {
        this.getType().commit((O) this, dataElements);
    }

    @SuppressWarnings("unchecked")
    public void commit(@NotNull L loadLevel) {
        this.getType().commit((O) this, loadLevel);
    }

    public @NotNull T getType() {
        return this.typeInstance;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull K getKey() throws DataNotLoadedException {
        return this.getType().getKey((O) this);
    }

    public @NotNull L getLoadLevel() {
        return this.loadLevel;
    }

    public void setLoadLevel(@Nullable L loadLevel) {
        this.loadLevel = loadLevel;
    }

    @Override
    @SuppressWarnings("unchecked")
    public long getId() {
        return this.getType().getId((O) this);
    }

}
