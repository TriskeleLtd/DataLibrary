package uk.m4xy.dataapi.test.tree;

import uk.m4xy.dataapi.api.data.cache.CacheExpiry;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.TypedFieldWrapper;
import uk.m4xy.dataapi.api.data.tree.LoadLevel;
import uk.m4xy.dataapi.api.distributions.Distribution;
import uk.m4xy.dataapi.api.util.RedisInformation;
import uk.m4xy.dataapi.impl.data.cache.distributed.redis.RedisCache;
import uk.m4xy.dataapi.impl.data.reflection.ReflectiveDataType;
import uk.m4xy.dataapi.impl.data.reflection.element.ReflectiveDataElement;
import uk.m4xy.dataapi.impl.data.reflection.element.constructor.DefaultElementConstructor;
import uk.m4xy.dataapi.impl.data.reflection.element.constructor.ReflectiveElementConstructor;
import uk.m4xy.dataapi.impl.data.reflection.element.constructor.annotation.AnnotatedFieldElementConstructor;
import uk.m4xy.dataapi.impl.data.reflection.element.constructor.annotation.ReflectiveElementTypeDefinition;

import java.util.Arrays;

public class FactionDataType extends ReflectiveDataType<FactionDataType, String, FactionData, FactionDataType.LoadLevels> {

    public FactionDataType() {
        super(new RedisCache<>(String.class, Distribution.getInstance(), CacheExpiry.NEVER, RedisInformation.get()),
                FactionData.class,
                ReflectiveElementConstructor.combine(
                        new AnnotatedFieldElementConstructor<>(),
                        new DefaultElementConstructor<FactionDataType, FactionData>(
                                getterSetter -> getterSetter instanceof TypedFieldWrapper fieldWrapper
                                        && Arrays.stream(fieldWrapper.getField().getAnnotations())
                                        .noneMatch(a -> a.getClass().isAnnotationPresent(ReflectiveElementTypeDefinition.class)),
                                ReflectiveDataElement::new)
                ));
    }

    @Override
    protected void registerOtherElements() {
    }


    public enum LoadLevels implements LoadLevel {
        ALWAYS,
        PLAYER_ONLINE,
        MODIFY;

        @Override
        public int getLevel() {
            return this.ordinal();
        }
    }

}