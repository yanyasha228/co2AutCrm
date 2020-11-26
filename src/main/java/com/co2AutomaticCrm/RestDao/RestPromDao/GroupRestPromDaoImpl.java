package com.co2AutomaticCrm.RestDao.RestPromDao;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestPromDto.GroupRestPromDto;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestPromModels.GroupsListPromRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class GroupRestPromDaoImpl implements GroupRestPromDao {

    @Value("${rest.prom.api.token}")
    private String apiToken;

    @Value("${rest.prom.api.get.groups.list}")
    private String getGroupsListUrl;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public List<GroupRestPromDto> getAllGroups() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", apiToken));
        HttpEntity<GroupsListPromRestResponse> entity = new HttpEntity<GroupsListPromRestResponse>(headers);
        ResponseEntity<GroupsListPromRestResponse> groupsRestListResponseEntity = restTemplate.exchange(getGroupsListUrl, HttpMethod.GET, entity, GroupsListPromRestResponse.class);
        if (groupsRestListResponseEntity.getStatusCode() == HttpStatus.OK) {
            if (groupsRestListResponseEntity.getBody() != null)
                return groupsRestListResponseEntity.getBody().getGroups();
        }
        return Collections.emptyList();
    }
}
