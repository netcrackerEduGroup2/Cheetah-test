package com.ncedu.cheetahtest.service.compound;

import com.ncedu.cheetahtest.dao.compound.CompoundDao;
import com.ncedu.cheetahtest.dao.libActCompound.LibActCompoundDao;
import com.ncedu.cheetahtest.entity.compound.Compound;

import com.ncedu.cheetahtest.entity.compound.DeleteCompoundDTO;
import com.ncedu.cheetahtest.entity.libActCompound.LibActCompound;
import com.ncedu.cheetahtest.exception.manageLibraries.UnproperInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompoundServiceImpl implements CompoundService {
    private final CompoundDao compoundDao;
    private final LibActCompoundDao libActCompoundDao;

    @Autowired
    public CompoundServiceImpl(CompoundDao compoundDao, LibActCompoundDao libActCompoundDao) {
        this.compoundDao = compoundDao;
        this.libActCompoundDao = libActCompoundDao;
    }

    @Override
    public void createCompound(int idLibrary ,Compound compoundDTO) {

        if (compoundDTO.getTitle() == null || (!compoundDTO.getStatus().equals("active") &&
                !compoundDTO.getStatus().equals("inactive"))){
            throw new UnproperInputException();
        } else {
            int idCompound = compoundDao.createCompound(compoundDTO);
            LibActCompound insert = new LibActCompound();
            insert.setIdLibrary(idLibrary);
            insert.setIdCompound(idCompound);
            libActCompoundDao.createLibActCompound(insert);
        }
    }

    @Override
    public List<Compound> selectAllCompound() {
        return compoundDao.selectAll();
    }

    @Override
    public Compound getCompoundId(int id) {
        return compoundDao.findCompoundById(id);
    }

    @Override
    public List<Compound> getCompoundByTitle(int idLibrary, String title) {
        return compoundDao.selectCompoundByTitle(idLibrary, title);
    }

    @Override
    public void editCompound(Compound compoundDTO) {
        compoundDao.editCompound(compoundDTO);
    }

    @Override
    public void changeStatus(String status, int id) {
        if(!status.equals("active") &&
                !status.equals("inactive")){
            throw new UnproperInputException();
        }
        else compoundDao.setStatus(status,id);
    }

    @Override
    public boolean isAdmin(String jwtToken) {

        //to do
        return false;
    }

    @Override
    public void deleteCompound(DeleteCompoundDTO deleteCompoundDTO) {
        // to do
    }
}
