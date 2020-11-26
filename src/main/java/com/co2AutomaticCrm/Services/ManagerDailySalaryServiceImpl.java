package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.Dao.ManagerDailySalaryDao;
import com.co2AutomaticCrm.Dao.SalaryIncreaseDao;
import com.co2AutomaticCrm.Models.ManagerDailySalary;
import com.co2AutomaticCrm.Models.ManagerSalaryIncrease;
import com.co2AutomaticCrm.Models.SalaryIncrease;
import com.co2AutomaticCrm.Models.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ManagerDailySalaryServiceImpl implements DailySalaryService<ManagerDailySalary> {

    @Autowired
    private ManagerDailySalaryDao managerDailySalaryDao;

    @Autowired
    private SalaryIncreaseDao<ManagerSalaryIncrease> managerSalaryIncreaseDao;

    @Autowired
    private SalaryIncreaseDao salaryIncreaseDao;

    @Autowired
    private WorkerService workerService;

    @Override
    public ManagerDailySalary save(ManagerDailySalary dailySalary) {

        ManagerDailySalary managerDailySalary = managerDailySalaryDao.save(dailySalary);


        List<SalaryIncrease> salaryIncreaseList = managerDailySalary.getSalaryIncreases();

        List<SalaryIncrease> salaryIncreasesDb = new ArrayList<>();

        for (SalaryIncrease salInc : salaryIncreaseList) {

            Optional<ManagerSalaryIncrease> salaryIncrease = managerSalaryIncreaseDao.findById(salInc.getId());

            if (salaryIncrease.isPresent()) {

                SalaryIncrease salaryIncreaseToMod = salaryIncrease.get();
                salaryIncreaseToMod.setDailySalary(managerDailySalary);

                salaryIncreasesDb.add(salaryIncreaseToMod);
            }


        }

        salaryIncreaseDao.saveAll(salaryIncreasesDb);

        return managerDailySalary;

    }

    @Override
    public Optional<ManagerDailySalary> findById(Long id) {
        return managerDailySalaryDao.findById(id);
    }

    @Override
    public List<ManagerDailySalary> findAll() {
        return managerDailySalaryDao.findAll();
    }

    @Override
    public Page<ManagerDailySalary> findDailySalariesByCreationDateWithPagination(LocalDate localDate, Pageable pageable) {
        return managerDailySalaryDao.findAllByCreationDate(localDate, pageable);
    }

    @Override
    public List<ManagerDailySalary> findDailySalariesByCreationDate(LocalDate localDate) {

        return managerDailySalaryDao.findAllByCreationDate(localDate);

    }

    @Override
    public Page<ManagerDailySalary> findAllWithPagination(Pageable pageable) {
        return managerDailySalaryDao.findAll(pageable);
    }

    @Override
    public Page<ManagerDailySalary> findDailySalariesByWorkerWithPagination(Worker worker, Pageable pageable) {
        return managerDailySalaryDao.findAllByWorker(worker, pageable);
    }

    @Override
    public Page<ManagerDailySalary> findDailySalariesByCreationDateAndWorkerWithPagination(LocalDate localDate, Worker worker, Pageable pageable) {

        return managerDailySalaryDao.findAllByCreationDateAndWorker(localDate, worker, pageable);

    }

    @Override
    public List<ManagerDailySalary> findDailySalariesByCreationDateAndWorker(LocalDate localDate, Worker worker) {
        return managerDailySalaryDao.findAllByCreationDateAndWorker(localDate, worker);
    }

    @Override
    public Page<ManagerDailySalary> findDailySalariesWithPagination(LocalDate localDate, Worker worker, Pageable pageable) {

        if (localDate == null) {

            if (worker == null) {
                return findAllWithPagination(pageable);

            } else return findDailySalariesByWorkerWithPagination(worker,
                    pageable);


        } else {

            if (worker == null) {
                return findDailySalariesByCreationDateWithPagination(localDate,
                        pageable);

            } else return findDailySalariesByCreationDateAndWorkerWithPagination(localDate,
                    worker,
                    pageable);
        }

    }

}
