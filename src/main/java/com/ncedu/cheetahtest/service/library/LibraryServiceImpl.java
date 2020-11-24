package com.ncedu.cheetahtest.service.library;

import com.ncedu.cheetahtest.dao.libActCompound.LibActCompoundDao;
import com.ncedu.cheetahtest.dao.library.LibraryDao;
import com.ncedu.cheetahtest.entity.library.Library;
import com.ncedu.cheetahtest.exception.manageLibraries.RightsPermissionException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {
    private final LibraryDao libraryDao;
    private final LibActCompoundDao libActCompoundDao;

    @Autowired
    public LibraryServiceImpl(LibraryDao libraryDao, LibActCompoundDao libActCompoundDao) {
        this.libraryDao = libraryDao;
        this.libActCompoundDao = libActCompoundDao;
    }


    @Override
    public List<Library> getAllLibraries() {
        return libraryDao.selectAll();
    }

    @Override
    public List<Library> getLibrariesByName(String name) {
        return libraryDao.selectLibrariesByName(name);
    }

    @Override
    public void createLibrary(Library libraryDTO) {
        libraryDao.createLibrary(libraryDTO);
    }

    @Override
    public void deleteLibrary(String token, int id) {
        if (isAdmin(token)) {
            libraryDao.removeLibrary(id);
            libActCompoundDao.removeByLibraryId(id);
        } else {
            throw new RightsPermissionException();
        }
    }

    @Override
    public Library editLibrary(Library library, int id) {
        libraryDao.setDescription(library.getDescription(),id);
        return libraryDao.setName(library.getName(),id);
    }

    @Override
    public boolean isAdmin(String jwtToken) {

        String[] split_string = jwtToken.split("\\.");
        String base64EncodedBody = split_string[1];
        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(base64EncodedBody));
        return body.contains("admin");
    }
}
