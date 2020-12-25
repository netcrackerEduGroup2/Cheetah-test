package com.ncedu.cheetahtest.entity.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationContainer<T> {
    List<T> elements;
    int totalElements;

}
