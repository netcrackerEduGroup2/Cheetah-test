package com.ncedu.cheetahtest.dao.libActCompound;

import com.ncedu.cheetahtest.entity.libActCompound.LibActCompound;

public interface LibActCompoundDao {
    void createLibActCompound(LibActCompound libActCompound);
    LibActCompound findLibActCompoundById(int id);
    void setIdCompound(int idCompound,int id);
    void setIdAction(int idAction, int id);
    void removeLibActCompound(int id);
}
