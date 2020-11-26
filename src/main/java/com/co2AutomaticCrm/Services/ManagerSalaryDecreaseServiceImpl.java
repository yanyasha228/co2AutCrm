package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.Dao.ManagerSalaryDecreaseDao;
import com.co2AutomaticCrm.Dao.WorkerDao;
import com.co2AutomaticCrm.Models.ManagerSalaryDecrease;
import com.co2AutomaticCrm.Models.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ManagerSalaryDecreaseServiceImpl implements SalaryDecreaseService<ManagerSalaryDecrease> {

    @Autowired
    private ManagerSalaryDecreaseDao salaryDecreaseDao;

    @Autowired
    private WorkerDao workerDao;

    @Override
    public ManagerSalaryDecrease save(ManagerSalaryDecrease salaryDecrease) {

        Worker worker = salaryDecrease.getWorker();
        worker.decreaseSalaryDecreaseBalance(salaryDecrease.getSalaryDecreaseAmount());
        workerDao.save(worker);

        return salaryDecreaseDao.save(salaryDecrease);

    }

    @Override
    public Optional<ManagerSalaryDecrease> findById(Long id) {
        return salaryDecreaseDao.findById(id);
    }

    @Override
    public Optional<ManagerSalaryDecrease> findByBitrixDealId(Long bitrixDealId) {
        return salaryDecreaseDao.findByBitrixDealId(bitrixDealId);
    }

    @Override
    public List<ManagerSalaryDecrease> findAllByWorker(Worker worker) {
        return salaryDecreaseDao.findAllByWorker(worker);
    }

    @Override
    public List<ManagerSalaryDecrease> findAll() {
        return salaryDecreaseDao.findAll();
    }

    @Override
    public Page<ManagerSalaryDecrease> findAllWithPagination(Pageable pageable) {
        return salaryDecreaseDao.findAll(pageable);
    }

    @Override
    public Page<ManagerSalaryDecrease> findAllByWorkerWithPagination(Worker worker, Pageable pageable) {
        return salaryDecreaseDao.findAllByWorker(worker, pageable);
    }

    @Override
    public Page<ManagerSalaryDecrease> findSalaryDecreasesWithPagination(Worker worker, Pageable pageable) {
        return Objects.isNull(worker) ? findAllWithPagination(pageable) : findAllByWorkerWithPagination(worker, pageable);
    }
}
