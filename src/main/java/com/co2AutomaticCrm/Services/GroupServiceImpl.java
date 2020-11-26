package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.Dao.GroupDao;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleEntitySaveUpdateException;
import com.co2AutomaticCrm.Models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

//    private static final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    private GroupDao groupDao;

    @Override
    public Group save(Group group) throws ImpossibleEntitySaveUpdateException {

        if (!Objects.isNull(group))
            if (group.getId() <= 0)
                throw new ImpossibleEntitySaveUpdateException("Attempt to update entity without ID!!!");

        return groupDao.save(group);
    }

    @Override
    public List<Group> save(List<Group> groupList) {

        List<Group> groupListForSave = groupList.stream().filter(group -> {
            if (group.getId() <= 0) {
//                log.warn("Attempt to update entity without ID!!!");
                return false;
            }
            return true;

        }).collect(Collectors.toList());

        return groupDao.saveAll(groupList);
    }

    @Override
    public Optional<Group> findById(int id) {
        return groupDao.findById(id);
    }

    @Override
    public List<Group> findAll() {
        return groupDao.findAll();
    }
}
