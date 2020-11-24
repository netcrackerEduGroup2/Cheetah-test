package com.ncedu.cheetahtest.entity.action;

import lombok.Data;

@Data
public class ChangeActionStatusDTO {
    private String statusToChange;
    private int id;
}
