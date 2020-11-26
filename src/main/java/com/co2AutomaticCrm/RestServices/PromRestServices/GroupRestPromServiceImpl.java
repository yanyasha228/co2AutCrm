package com.co2AutomaticCrm.RestServices.PromRestServices;

import com.co2AutomaticCrm.Models.Group;
import com.co2AutomaticCrm.RestDao.RestPromDao.GroupRestPromDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class GroupRestPromServiceImpl implements GroupRestPromService {

    @Autowired
    private GroupRestPromDao groupRestDao;

//    @Autowired
//    private GroupRestPromDtoMapper groupMapper;

    @Autowired
    private ModelMapper groupMapper;

    @Override
    public List<Group> getAll() {
        return groupRestDao.getAllGroups().stream().
                map(groupDto -> groupMapper.map(groupDto, Group.class)).collect(Collectors.toList());
    }
}
