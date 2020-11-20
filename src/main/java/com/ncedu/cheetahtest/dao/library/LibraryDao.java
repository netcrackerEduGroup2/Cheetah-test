package com.ncedu.cheetahtest.dao.library;

import com.ncedu.cheetahtest.entity.library.Library;

import java.util.List;

public interface LibraryDao {
    void createLibrary(Library library);

    Library findLibraryById(int id);

    List<Library> selectAll();

    List<Library> selectLibrariesByName(String name);

    void setDescription(String description, int id);
    void setName(String name, int id);

    void removeLibrary(int id);
}
