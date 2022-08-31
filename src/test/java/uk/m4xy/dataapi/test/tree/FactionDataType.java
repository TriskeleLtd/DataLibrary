package uk.m4xy.dataapi.test.tree;

import uk.m4xy.dataapi.api.data.cache.CacheExpiry;
import uk.m4xy.dataapi.api.data.persist.leveled.LoadLevel;
import uk.m4xy.dataapi.api.distributions.Distribution;
import uk.m4xy.dataapi.api.util.RedisInformation;
import uk.m4xy.dataapi.impl.data.cache.distributed.redis.RedisCache;
import uk.m4xy.dataapi.impl.data.reflection.ReflectiveDataType;
import uk.m4xy.dataapi.impl.data.reflection.element.constructor.DefaultElementConstructor;
import uk.m4xy.dataapi.impl.data.reflection.element.constructor.ReflectiveElementConstructor;
import uk.m4xy.dataapi.impl.data.reflection.element.constructor.annotation.AnnotatedFieldElementConstructor;

public class FactionDataType extends ReflectiveDataType<FactionDataType, String, FactionData, FactionDataType.LoadLevels> {

    public FactionDataType() {
        super(new RedisCache<>(String.class, Distribution.getInstance(), CacheExpiry.NEVER, RedisInformation.get()), FactionData.class, ReflectiveElementConstructor.combine(new AnnotatedFieldElementConstructor<>(), DefaultElementConstructor.<FactionDataType, FactionData>get()));


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