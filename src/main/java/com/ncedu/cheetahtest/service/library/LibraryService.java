package com.ncedu.cheetahtest.service.library;

import com.ncedu.cheetahtest.entity.library.ChangeLibraryDTO;
import com.ncedu.cheetahtest.entity.library.Library;

import java.util.List;

public interface LibraryService {
    List<Library> getAllLibraries();
    List<Library> getLibrariesByName(String name);
    void createLibrary(Library libraryDTO);
    void deleteLibrary(String token, int id);
    boolean isAdmin(String token);
    Library editLibrary(Library library, int id);
}
