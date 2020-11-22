package com.ncedu.cheetahtest.service.library;

import com.ncedu.cheetahtest.dao.library.LibraryDao;
import com.ncedu.cheetahtest.entity.library.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {
    private final LibraryDao libraryDao;

    @Autowired
    public LibraryServiceImpl(LibraryDao libraryDao) {
        this.libraryDao = libraryDao;
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


}
