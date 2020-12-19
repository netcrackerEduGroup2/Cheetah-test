package com.ncedu.cheetahtest.entity.pagination;

import lombok.Data;

import java.util.List;

@Data
public class PaginationContainer<T> {
    int totalElements;
    List<T> elements;
}
