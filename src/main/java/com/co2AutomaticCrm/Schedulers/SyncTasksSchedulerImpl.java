package com.co2AutomaticCrm.Schedulers;

import com.co2AutomaticCrm.Handlers.HandlersUtils.WorkerDailySalaryCounter;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ProductXmlCatalogInaccessibilityException;
import com.co2AutomaticCrm.SyncUtils.ProductsSynchronizer;
import com.co2AutomaticCrm.SyncUtils.RozetkaCatalogXmlSynchronizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class SyncTasksSchedulerImpl implements SyncTasksScheduler {

    private static Logger logger = LoggerFactory.getLogger(SyncTasksSchedulerImpl.class);

    @Autowired
    private ProductsSynchronizer productsSynchronizer;

    @Autowired
    private RozetkaCatalogXmlSynchronizer rozetkaCatalogXmlSynchronizer;

    @Autowired
    @Qualifier("managerDailySalaryCounter")
    private WorkerDailySalaryCounter workerDailySalaryCounter;

    @Scheduled(cron = "0 0 5 * * ?")
    @Override
    public void synchronizeProductsPromDbBitrix() {
        logger.error("Start synchronizeProductsPromDbBitrix() : " + LocalTime.now());
        productsSynchronizer.sync();
        logger.error("End synchronizeProductsPromDbBitrix() :" + LocalTime.now());

    }

    @Scheduled(cron = "0 0 17 * * ?")
    @Override
    public void countManagersDailySalary() {
        logger.error("Start countManagersDailySalary() : " + LocalTime.now());
        workerDailySalaryCounter.count();
        logger.error("End countManagersDailySalary() : " + LocalTime.now());

    }

    @Scheduled(fixedDelay = 1800000)
    @Override
    public void synchronizeRozetkaCatalog() {
        logger.error("Start synchronizeRozetkaCatalog() : " + LocalTime.now());
        try {
            System.out.println("Start Xml sync" + LocalTime.now());

            rozetkaCatalogXmlSynchronizer.generateAndSave();

            System.out.println("Finish Xml sync" + LocalTime.now());
        } catch (ProductXmlCatalogInaccessibilityException e) {
            e.printStackTrace();
        }
        logger.error("End synchronizeRozetkaCatalog() : " + LocalTime.now());

    }
}
