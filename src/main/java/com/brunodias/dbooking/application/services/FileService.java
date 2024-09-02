package com.brunodias.dbooking.application.services;

import com.brunodias.dbooking.domain.services.IFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService implements IFileService {
    @Value("${application.file.upload-dir}")
    private String uploadDir;


    @Override
    public String saveFile(MultipartFile file) throws IOException {
        // Cria o diretório se ele não existir
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        // Gera um nome único para o arquivo
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File serverFile = new File(directory.getAbsolutePath() + File.separator + fileName);
        // Salva o arquivo no diretório especificado
        file.transferTo(serverFile);
        // Retorna o caminho do arquivo salvo
        return serverFile.getAbsolutePath();    }

    @Override
    public File getFile(String filename) {
        return null;
    }

    @Override
    public boolean isFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public String getUploadDir() {
        return uploadDir;
    }


}
