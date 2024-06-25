package com.example.upload_download.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@org.springframework.stereotype.Service
public class ApplicationService {

    @Value("${fileRepositoryFolder}")
    public String fileRepositoryFolder;




    public String upload(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString() + "." + extension;
        File finalFolder = new File(fileRepositoryFolder);
        if(!finalFolder.exists()) throw new IOException("Final folder does not exist");
        if(!finalFolder.isDirectory()) throw new IOException("Final folder is not a directory");
        File finalDestination = new File(fileRepositoryFolder + "\\" + newFileName);
        if(finalDestination.exists()) throw new IOException("File conflict");
        file.transferTo(finalDestination);
        return newFileName;
    }

    public byte[] download(String fileName) throws IOException {
        File fileFromRepository = new File(fileRepositoryFolder + "\\" + fileName);
        if (!fileFromRepository.exists()) throw new IOException("File doesn't exists");
        return IOUtils.toByteArray(new FileInputStream(fileFromRepository));
    }
}
