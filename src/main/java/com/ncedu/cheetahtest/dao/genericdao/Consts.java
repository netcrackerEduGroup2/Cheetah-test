package com.ncedu.cheetahtest.dao.genericdao;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public abstract class Consts {

    private final Map<CommonQuery, String> constMap;

}
