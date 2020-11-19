package com.ncedu.cheetahtest.dao.library;

import com.ncedu.cheetahtest.entity.library.Library;

public interface LibraryDao {
    void createLibrary(Library library);

    Library findLibraryById(int id);

    void setDescription(String description, int id);

    void removeLibrary(int id);
}
