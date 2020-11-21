package com.ncedu.cheetahtest.entity.testcase;

import lombok.Data;

@Data
public class TestCase {
    private int id;
    private int projectId;
    // TODO: 21.11.2020 Change 'status' to ENUM type
    private String status;
}
