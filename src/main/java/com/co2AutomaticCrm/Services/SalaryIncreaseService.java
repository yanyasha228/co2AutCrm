package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.Models.SalaryIncrease;
import com.co2AutomaticCrm.Models.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SalaryIncreaseService<T extends SalaryIncrease> {

    T save(T salaryIncrease);

    List<T> findAllByDailySalaryId(Long id);

    Page<T> findAllByDailySalaryIdWithPagination(Long id, Pageable pageable);

    Optional<T> findById(Long id);

    List<T> findAllByWorker(Worker worker);

    List<T> findAll();

    Page<T> findAllWithPagination(Pageable pageable);

    Page<T> findAllByWorkerWithPagination(Worker worker, Pageable pageable);

    Optional<T> findByBitrixDealId(Long bitrixDealId);

    Page<T> findSalaryIncreasesWithPagination(Worker worker, Pageable pageable);

    List<T> findAllByCreationDate(LocalDate localDate);

}
