package com.ncedu.cheetahtest.entity.dashboard.user;

import com.ncedu.cheetahtest.entity.testscenario.ItemKind;
import lombok.Data;

@Data
public class UserDateDTO {

    private int id;
    private String name;
    private int priority;
    private String description;
    private ItemKind kind;

}
