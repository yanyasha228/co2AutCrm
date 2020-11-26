package com.co2AutomaticCrm.Dao;

import com.co2AutomaticCrm.Models.SalaryIncrease;
import com.co2AutomaticCrm.Models.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SalaryIncreaseDao<T extends SalaryIncrease> extends JpaRepository<T, Long> {


    List<T> findAllByWorker(Worker worker);

    Optional<T> findById(Long id);

    Page<T> findAllByWorker(Worker worker, Pageable pageable);

    Page<T> findAll(Pageable pageable);

    List<T> findAllByDailySalaryId(Long id);

    Page<T> findAllByDailySalaryId(Long id, Pageable pageable);

    List<T> findByCreationDate(LocalDate localDate);

}
