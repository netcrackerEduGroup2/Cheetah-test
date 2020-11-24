package com.ncedu.cheetahtest.service.compound;

import com.ncedu.cheetahtest.dao.compound.CompoundDao;
import com.ncedu.cheetahtest.dao.libactcompound.LibActCompoundDao;
import com.ncedu.cheetahtest.entity.compound.Compound;
import com.ncedu.cheetahtest.entity.libactcompound.LibActCompound;
import com.ncedu.cheetahtest.exception.managelibraries.RightsPermissionException;
import com.ncedu.cheetahtest.exception.managelibraries.UnproperInputException;
import com.ncedu.cheetahtest.service.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompoundServiceImpl implements CompoundService {
    private final CompoundDao compoundDao;
    private final LibActCompoundDao libActCompoundDao;
    private final AuthService authService;

    @Autowired
    public CompoundServiceImpl(CompoundDao compoundDao, LibActCompoundDao libActCompoundDao, AuthService authService) {
        this.compoundDao = compoundDao;
        this.libActCompoundDao = libActCompoundDao;
        this.authService = authService;
    }

    @Override
    public void createCompound(int idLibrary, Compound compoundDTO) {

        if (compoundDTO.getTitle() == null || (!"active".equals(compoundDTO.getStatus())&&
                !"inactive".equals(compoundDTO.getStatus())) ){
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
    public List<Compound> getActiveCompoundByTitle(int idLibrary, String title) {
        return compoundDao.selectActiveCompoundByTitle(idLibrary, title);
    }

    @Override
    public List<Compound> getInactiveCompoundByTitle(int idLibrary, String title) {
        return compoundDao.selectInactiveCompoundByTitle(idLibrary, title);
    }

    @Override
    public Compound editCompound(Compound compoundDTO) {
        if(!"active".equals(compoundDTO.getStatus()) &&
                !"inactive".equals(compoundDTO.getStatus())){
            throw new UnproperInputException();
        }
        else{
            return compoundDao.editCompound(compoundDTO);
        }

    }

    @Override
    public Compound changeStatus(String status, int id) {
        if (!"active".equals(status) &&
                !"inactive".equals(status)) {
            throw new UnproperInputException();
        } else return compoundDao.setStatus(status, id);
    }



    @Override
    public void deleteCompound(String token ,int idCompound) {
        if (authService.isAdmin(token)){
            compoundDao.removeCompoundById(idCompound);
            libActCompoundDao.removeByCompoundId(idCompound);
        }
        else throw new RightsPermissionException();
    }
}
