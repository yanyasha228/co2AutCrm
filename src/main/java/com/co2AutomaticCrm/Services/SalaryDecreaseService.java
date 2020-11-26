package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.Models.SalaryDecrease;
import com.co2AutomaticCrm.Models.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SalaryDecreaseService<T extends SalaryDecrease> {

    T save(T salaryDecrease);

    Optional<T> findById(Long id);

    Optional<T> findByBitrixDealId(Long bitrixDealId);

    List<T> findAllByWorker(Worker worker);

    List<T> findAll();

    Page<T> findAllWithPagination(Pageable pageable);

    Page<T> findAllByWorkerWithPagination(Worker worker, Pageable pageable);

    Page<T> findSalaryDecreasesWithPagination(Worker worker, Pageable pageable);

}
