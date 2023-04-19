package uk.m4xy.dataapi.test.core;

import uk.m4xy.dataapi.api.data.annotation.Id;
import uk.m4xy.dataapi.api.data.annotation.Key;
import uk.m4xy.dataapi.api.data.cache.CacheExpiry;
import uk.m4xy.dataapi.impl.data.cache.distributed.redis.AnnotatedRedisCache;
import uk.m4xy.dataapi.test.Level;

import java.util.UUID;

@SQLTable("core_player")
@AnnotatedRedisCache(cacheExpiry = CacheExpiry.TEN_MINUTES_MILLIS, channel = "CORE_PLAYER")
public interface CorePlayer {

    @Id
    @LoadLevel(Level.ALWAYS)
    @TempLoadLevel(TempLoads.TEN_MINUTES)
    @SQLKey
    long getId();

    long timeOnlineToday();

    @Key
    @SQLKey
    UUID getUuid();

    @SQLKey
    long getPlayTime();

    @SQLKey
    long getFirstOnline();

    @SQLKey
    long getLastOnline();

    @SQLKey
    @AlwaysPush
    String getUsername();

    @SQLKey
    @AlwaysPush
    String getRank();

    @SQLKey("rank")
    void setRank(String rank);

    @SQLKey
    @DoNotInvalidateCaches
    long getWeeklyPlayTime();

    @SQLKey
    String getIp();

    /*

    Server1 - CorePlayer(1) - rank = newRank -> invalidateElsewhere();
    Server2 -

       getRankWhatever = rank_whatever;

       isWhite = white
       hasProperty = property;

     */



}
