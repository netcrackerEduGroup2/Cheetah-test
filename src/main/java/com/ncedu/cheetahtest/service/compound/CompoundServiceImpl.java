package com.ncedu.cheetahtest.service.compound;

import com.ncedu.cheetahtest.dao.compound.CompoundDao;
import com.ncedu.cheetahtest.entity.compound.Compound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompoundServiceImpl implements CompoundService{
    private  final CompoundDao compoundDao;

    @Autowired
    public CompoundServiceImpl(CompoundDao compoundDao) {
        this.compoundDao = compoundDao;
    }

    @Override
    public void createCompound(Compound compoundDTO) {
        compoundDao.createCompound(compoundDTO);
    }
}
