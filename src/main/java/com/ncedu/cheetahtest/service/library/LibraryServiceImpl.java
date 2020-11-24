package com.ncedu.cheetahtest.service.library;

import com.ncedu.cheetahtest.dao.libactcompound.LibActCompoundDao;
import com.ncedu.cheetahtest.dao.library.LibraryDao;
import com.ncedu.cheetahtest.entity.library.Library;
import com.ncedu.cheetahtest.exception.managelibraries.RightsPermissionException;
import com.ncedu.cheetahtest.service.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {
    private final LibraryDao libraryDao;
    private final LibActCompoundDao libActCompoundDao;
    private final AuthService authService;

    @Autowired
    public LibraryServiceImpl(LibraryDao libraryDao, LibActCompoundDao libActCompoundDao,AuthService authService) {
        this.libraryDao = libraryDao;
        this.libActCompoundDao = libActCompoundDao;
        this.authService = authService;
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
        if (authService.isAdmin(token)) {
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


}
