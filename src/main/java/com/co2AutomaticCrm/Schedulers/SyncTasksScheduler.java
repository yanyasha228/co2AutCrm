package com.co2AutomaticCrm.Schedulers;

public interface SyncTasksScheduler {

    void synchronizeProductsPromDbBitrix();

    void countManagersDailySalary();

    void synchronizeRozetkaCatalog();
}
