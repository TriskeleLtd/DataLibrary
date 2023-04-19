package uk.m4xy.dataapi.test.core;


import dev.triskele.common.environment.annotation.EnvironmentPath;
import uk.m4xy.dataapi.api.data.annotation.Id;
import uk.m4xy.dataapi.api.data.annotation.Key;
import uk.m4xy.dataapi.impl.data.cache.distributed.redis.AnnotatedRedisCache;

import java.util.Map;
import java.util.UUID;

import static uk.m4xy.dataapi.test.Level.*;

@AnnotatedRedisCache(channel = "EXAMPLE")
@SQLTable("example_table")
public interface ExampleData {
    @EnvironmentPath("GAS")
    @LoadLevel(NEVER) // Pulls everytime, doesn't create a variable
    String getGas();

    @SQLKey // infers id from getter
    @LoadLevel(ALWAYS)
    @Id
    long getId();

    @SQLKey
    @LoadLevel(ALWAYS)
    @Key
    UUID getUUID();

    @SQLKey
    @LoadLevel(PRIMARY_LOAD)
    String getName();
    @SQLKey
    @AlwaysPush
    void setName(String name);

    @SQLKey("playtime")
    @LoadLevel(PRIMARY_LOAD)
    @RunTaskOnLoad(PlaytimeTask.class)
    long getPlaytime();

    @SQLKey("playtime")
    @LazyPush
    @DoNotInvalidateCaches
    void setPlaytime();

    @SQLTable("example_weights")
    @LoadLevel(CACHE_ON_REQUEST)
    @SQLColumnReference(type = ExampleData.class, column = "example_id")
    @SQLMapKey(keyColumn = "value", valueColumn = "weight")
    Map<Integer, Integer> getWeights();
    // Hey from, example_weights. Fetch me all the
    @MapPutter
    @SQLTable("example_weights")
    @SQLMapKey(keyColumn = "value", valueColumn = "weight")
    void setWeight(int value, int weight);

}
