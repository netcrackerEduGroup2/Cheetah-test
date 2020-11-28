package com.ncedu.cheetahtest.dao.genericdao;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public abstract class Consts {

    @NonNull private Map<StableQuery, String> constMap;

}
