package com.ncedu.cheetahtest.entity.action;

import com.ncedu.cheetahtest.entity.compound.Compound;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationResponseBody {
    private List<Object> list;
    private int totalElements;

    public PaginationResponseBody(List<Action> actions, List<Compound> compounds, int size, int page) {
        List<Object> combined = new ArrayList<>(actions);
        combined.addAll(compounds);
        List<Object> res;
        if (page*size >= combined.size()) {
            res = new ArrayList<>(combined);
        } else {

            res = combined.subList(size * (page - 1), size * page);
        }
        this.list = res;
        this.totalElements = combined.size();
    }
}
