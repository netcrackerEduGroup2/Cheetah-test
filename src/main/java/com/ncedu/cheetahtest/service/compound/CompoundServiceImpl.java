package com.ncedu.cheetahtest.service.compound;

import com.ncedu.cheetahtest.dao.compound.CompoundDao;
import com.ncedu.cheetahtest.dao.libactcompound.LibActCompoundDao;
import com.ncedu.cheetahtest.entity.compound.Compound;

import com.ncedu.cheetahtest.entity.compound.DeleteCompoundDTO;
import com.ncedu.cheetahtest.entity.libactcompound.LibActCompound;
import com.ncedu.cheetahtest.exception.managelibraries.RightsPermissionException;
import com.ncedu.cheetahtest.exception.managelibraries.UnproperInputException;
import org.apache.tomcat.util.codec.binary.Base64;
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
    public void createCompound(int idLibrary, Compound compoundDTO) {

        if (compoundDTO.getTitle() == null || (!compoundDTO.getStatus().equals("active") &&
                !compoundDTO.getStatus().equals("inactive"))) {
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
        if(!compoundDTO.getStatus().equals("active") &&
                !compoundDTO.getStatus().equals("inactive")){
            throw new UnproperInputException();
        }
        else{
            return compoundDao.editCompound(compoundDTO);
        }

    }

    @Override
    public Compound changeStatus(String status, int id) {
        if (!status.equals("active") &&
                !status.equals("inactive")) {
            throw new UnproperInputException();
        } else return compoundDao.setStatus(status, id);
    }

    @Override
    public boolean isAdmin(String jwtToken) {

        String[] split_string = jwtToken.split("\\.");
        String base64EncodedBody = split_string[1];
        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(base64EncodedBody));
        return body.contains("admin");
    }

    @Override
    public void deleteCompound(String token ,DeleteCompoundDTO deleteCompoundDTO) {
        if (isAdmin(token)){
            compoundDao.removeCompoundById(deleteCompoundDTO.getId());
            libActCompoundDao.removeByCompoundId(deleteCompoundDTO.getId());
        }
        else throw new RightsPermissionException();
    }
}
