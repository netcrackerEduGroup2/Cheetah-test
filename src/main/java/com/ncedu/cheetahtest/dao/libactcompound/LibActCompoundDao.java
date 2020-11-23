package com.ncedu.cheetahtest.dao.libactcompound;

import com.ncedu.cheetahtest.entity.libactcompound.LibActCompound;

import java.util.List;

public interface LibActCompoundDao {
    void createLibActCompound(LibActCompound libActCompound);
    List<LibActCompound> findLibActCompoundsById(int id);
    List<LibActCompound> findLibActCompoundsByIdCompound(int id);
    List<LibActCompound> findLibActCompoundsByIdAct(int id);
    void setIdCompound(int idCompound,int id);
    void setIdAction(int idAction, int id);
    void removeByLibraryId(int idLib);
    void removeByCompoundId(int idComp);
    void removeByActionId(int idAct);
}
