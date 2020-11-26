package com.co2AutomaticCrm.Dao;

import com.co2AutomaticCrm.Models.DailySalary;
import com.co2AutomaticCrm.Models.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailySalaryDao<T extends DailySalary> extends JpaRepository<T, Long> {

    Optional<T> findById(Long id);

    List<T> findAll();

    List<T> findAllByWorker(Worker worker);

    Page<T> findAll(Pageable pageable);

    Page<T> findAllByWorker(Worker worker, Pageable pageable);

    Page<T> findAllByCreationDate(LocalDate localDate, Pageable pageable);

    List<T> findAllByCreationDate(LocalDate localDate);

    Page<T> findAllByCreationDateAndWorker(LocalDate localDate, Worker worker, Pageable pageable);

    List<T> findAllByCreationDateAndWorker(LocalDate localDate, Worker worker);


}
