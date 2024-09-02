package com.brunodias.dbooking.domain.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface IFileService {
    String saveFile(MultipartFile file) throws IOException;

    File getFile(String filename);

    boolean isFileExists(String filePath);

    String getUploadDir() throws IOException;
}
