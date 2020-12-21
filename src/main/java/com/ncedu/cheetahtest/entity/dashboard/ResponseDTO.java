package com.ncedu.cheetahtest.entity.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class ResponseDTO {
    private List<ProjectActivityDTO> series;
}
