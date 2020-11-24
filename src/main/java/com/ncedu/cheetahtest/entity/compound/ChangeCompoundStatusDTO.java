package com.ncedu.cheetahtest.entity.compound;

import lombok.Data;

@Data
public class ChangeCompoundStatusDTO {
    private String statusToChange;
    private int id;
}
