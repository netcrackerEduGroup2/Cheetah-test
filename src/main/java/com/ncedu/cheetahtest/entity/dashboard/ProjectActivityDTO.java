package com.ncedu.cheetahtest.entity.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectActivityDTO {
    @JsonProperty("name")
    private String date;
    @JsonProperty("value")
    private int count;
}
