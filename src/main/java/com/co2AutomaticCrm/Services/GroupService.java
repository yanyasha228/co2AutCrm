package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleEntitySaveUpdateException;
import com.co2AutomaticCrm.Models.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    Group save(Group group) throws ImpossibleEntitySaveUpdateException;

    List<Group> save(List<Group> groupList);

    Optional<Group> findById(int id);

    List<Group> findAll();

}
