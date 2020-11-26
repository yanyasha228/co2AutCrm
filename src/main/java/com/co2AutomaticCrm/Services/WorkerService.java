package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.Models.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface WorkerService extends UserService<Worker> {

    List<Worker> findAll();

    Page<Worker> findAllWithPagination(Pageable pageable);

    void saveUser(Worker user);

    void saveUserWithPass(Worker user, String password);

    Optional<Worker> findById(Long id);

    Worker save(Worker worker);

}