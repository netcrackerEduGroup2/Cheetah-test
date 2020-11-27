package com.ncedu.cheetahtest.entity.library;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LibraryPaginationResponseBody {
    private List<Object> list;
    private int totalElements;

    public LibraryPaginationResponseBody(List<Library> libraries, int size, int page) {
        List<Object> res;
        if (size*page >= libraries.size()) {
            res = new ArrayList<>(libraries);
        } else {
            res = new ArrayList<>(libraries.subList(size * (page - 1), size * page));
        }
        this.list = res;
        this.totalElements = libraries.size();
    }
}
