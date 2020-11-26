package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestPromModels;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestPromDto.GroupRestPromDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class GroupsListPromRestResponse {

    private List<GroupRestPromDto> groups = new ArrayList<GroupRestPromDto>();

    private ErrorsPromRestResponse errors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupsListPromRestResponse that = (GroupsListPromRestResponse) o;
        return Objects.equals(groups, that.groups) &&
                Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groups, errors);
    }
}
