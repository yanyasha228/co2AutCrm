package com.co2AutomaticCrm.Services;


import com.co2AutomaticCrm.Models.DailySalary;
import com.co2AutomaticCrm.Models.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailySalaryService<T extends DailySalary> {

    T save(T dailySalary);

    Optional<T> findById(Long id);

    List<T> findAll();

    Page<T> findDailySalariesByCreationDateWithPagination(LocalDate localDate, Pageable pageable);

    List<T> findDailySalariesByCreationDate(LocalDate localDate);

    Page<T> findAllWithPagination(Pageable pageable);

    Page<T> findDailySalariesByWorkerWithPagination(Worker worker, Pageable pageable);

    Page<T> findDailySalariesByCreationDateAndWorkerWithPagination(LocalDate localDate, Worker worker, Pageable pageable);

    List<T> findDailySalariesByCreationDateAndWorker(LocalDate localDate, Worker worker);

    Page<T> findDailySalariesWithPagination(LocalDate localDate, Worker worker, Pageable pageable);


}
