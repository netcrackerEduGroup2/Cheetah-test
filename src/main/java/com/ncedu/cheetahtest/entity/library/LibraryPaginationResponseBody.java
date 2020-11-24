package com.ncedu.cheetahtest.entity.library;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LibraryPaginationResponseBody {
    private List list;
    private int totalElements;

    public LibraryPaginationResponseBody(List<Library> libraries, int size, int page) {
        List res;
        if (size * page >= libraries.size()) {
            res = new ArrayList(libraries);
        } else {
            res = libraries.subList(size * (page - 1), size * page);
        }
        this.list = res;
        this.totalElements = res.size();
    }
}
