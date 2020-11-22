package com.ncedu.cheetahtest.entity.action;

import lombok.Data;

import java.util.List;

@Data
public class PaginationResponceBody {
    private List list;
    private int totalElements;
}
