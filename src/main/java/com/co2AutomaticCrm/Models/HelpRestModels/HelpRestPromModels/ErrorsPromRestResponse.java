package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestPromModels;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ErrorsPromRestResponse {

    @JsonAnySetter
    private Map<Integer, Map<String, String>> errors;

    @JsonAnyGetter
    public Map<Integer, Map<String, String>> getErrors() {
        return errors;
    }

    public void setErrors(Map<Integer, Map<String, String>> errors) {
        this.errors = errors;
    }

}
