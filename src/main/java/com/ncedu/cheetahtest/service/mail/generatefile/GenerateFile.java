package com.ncedu.cheetahtest.service.mail.generatefile;


import java.io.FileNotFoundException;
import java.io.IOException;

public interface GenerateFile {

    void createFile(String filePath)  throws FileNotFoundException, IOException;

    void closeFile();
}
