package uk.m4xy.dataapi.api.util.maintenance;

import java.util.*;
import java.util.concurrent.*;

public interface Maintainable {
    Set<Maintainable> MAINTAINED = Collections.newSetFromMap(new ConcurrentHashMap<>());
    ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    static void startMaintenanceTask() {
        EXECUTOR_SERVICE.scheduleAtFixedRate(() -> MAINTAINED.forEach(x -> {
            try {
                x.maintain();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }), 5L, 5L, TimeUnit.MINUTES);
    }

    static void finishMaintenanceTask() {
        EXECUTOR_SERVICE.shutdown();
    }

    static void register(Maintainable maintainable) {
        MAINTAINED.add(maintainable);
    }

    default void registerMaintenance(Maintainable maintainable) {
        register(maintainable);
    }
    void maintain();

}
