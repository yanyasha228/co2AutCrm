package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.Dao.WorkerDao;
import com.co2AutomaticCrm.Models.ModelEnums.Role;
import com.co2AutomaticCrm.Models.User;
import com.co2AutomaticCrm.Models.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class WorkerServiceImpl implements WorkerService {


    private final static Map<Integer, Set<Role>> departmentIdMap = new HashMap<Integer, Set<Role>>() {

        {
            put(1, new HashSet<Role>(Arrays.asList(Role.ADMIN, Role.MANAGER, Role.STOREKEEPER, Role.GUN_MASTER)));
            put(5, new HashSet<Role>(Arrays.asList(Role.MANAGER)));
            put(12, new HashSet<Role>(Arrays.asList(Role.GUN_MASTER)));
            put(10, new HashSet<Role>(Arrays.asList(Role.STOREKEEPER)));
        }

    };

    @Autowired
    private WorkerDao workerDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = workerDao.findByUsername(username);


        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }


        return user;

    }


    @Override
    public List<Worker> findAll() {
        return workerDao.findAll();
    }

    @Override
    public Page<Worker> findAllWithPagination(Pageable pageable) {
        return workerDao.findAll(pageable);
    }


    @Override
    public void saveUser(Worker user) {

        user.setUsername(user.getEmail());

        for (Integer depId : user.getBitrixDepartmentsIds()) user.getRoles().addAll(departmentIdMap.get(depId));

        workerDao.save(user);

    }

    @Override
    public void saveUserWithPass(Worker user, String password) {

        user.setPassword(passwordEncoder.encode(password));

        user.setUsername(user.getEmail());

        for (Integer depId : user.getBitrixDepartmentsIds()) user.getRoles().addAll(departmentIdMap.get(depId));

        workerDao.save(user);
    }

    @Override
    public Optional<Worker> findById(Long id) {
        return workerDao.findById(id);
    }

    @Override
    public Worker save(Worker worker) {
        return workerDao.save(worker);
    }


}
