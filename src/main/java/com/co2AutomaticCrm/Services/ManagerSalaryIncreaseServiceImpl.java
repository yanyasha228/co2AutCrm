package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.Dao.ManagerSalaryIncreaseDao;
import com.co2AutomaticCrm.Models.ManagerSalaryIncrease;
import com.co2AutomaticCrm.Models.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ManagerSalaryIncreaseServiceImpl implements SalaryIncreaseService<ManagerSalaryIncrease> {

    @Autowired
    private ManagerSalaryIncreaseDao salaryIncreaseDao;

    @Override
    public ManagerSalaryIncrease save(ManagerSalaryIncrease salaryIncrease) {
        return salaryIncreaseDao.save(salaryIncrease);
    }

    @Override
    public List<ManagerSalaryIncrease> findAllByDailySalaryId(Long id) {
        return salaryIncreaseDao.findAllByDailySalaryId(id);
    }

    @Override
    public Page<ManagerSalaryIncrease> findAllByDailySalaryIdWithPagination(Long id, Pageable pageable) {
        return salaryIncreaseDao.findAllByDailySalaryId(id, pageable);
    }

    @Override
    public Optional<ManagerSalaryIncrease> findById(Long id) {

        return salaryIncreaseDao.findById(id);

    }

    @Override
    public List<ManagerSalaryIncrease> findAllByWorker(Worker worker) {

        return salaryIncreaseDao.findAllByWorker(worker);

    }

    @Override
    public List<ManagerSalaryIncrease> findAll() {
        return salaryIncreaseDao.findAll();
    }

    @Override
    public Page<ManagerSalaryIncrease> findAllWithPagination(Pageable pageable) {
        return salaryIncreaseDao.findAll(pageable);
    }

    @Override
    public Page<ManagerSalaryIncrease> findAllByWorkerWithPagination(Worker worker, Pageable pageable) {
        return salaryIncreaseDao.findAllByWorker(worker, pageable);
    }

    @Override
    public Optional<ManagerSalaryIncrease> findByBitrixDealId(Long bitrixDealId) {

        return salaryIncreaseDao.findByBitrixDealId(bitrixDealId);

    }

    @Override
    public Page<ManagerSalaryIncrease> findSalaryIncreasesWithPagination(Worker worker, Pageable pageable) {

        return Objects.isNull(worker) ? findAllWithPagination(pageable) : findAllByWorkerWithPagination(worker, pageable);

    }

    @Override
    public List<ManagerSalaryIncrease> findAllByCreationDate(LocalDate localDate) {
        return salaryIncreaseDao.findByCreationDate(localDate);
    }
}
