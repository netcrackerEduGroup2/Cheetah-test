package com.ncedu.cheetahtest.entity.action;

import com.ncedu.cheetahtest.entity.compound.Compound;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationResponseBody {
    private List list;
    private int totalElements;

    public PaginationResponseBody(List<Action> actions, List<Compound> compounds, int size, int page) {
        List combined = new ArrayList(actions);
        combined.addAll(compounds);
        List res;
        if (size * page >= combined.size()) {
            res = new ArrayList(combined);
        } else {
            res = combined.subList(size * (page - 1), size * page);
        }
        this.list = res;
        this.totalElements = res.size();
    }
}
