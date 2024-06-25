package com.example.upload_download.controllers;

import com.example.upload_download.services.ApplicationService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/files")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/upload")
    public List<String> upload(@RequestParam("File") MultipartFile[] files) throws IOException {
        List<String> fileNames = new ArrayList<>();
        for(MultipartFile file : files) {
            String singleUploadedFileName = applicationService.upload(file);
            fileNames.add(singleUploadedFileName);
        }
        return fileNames;
    }

    @GetMapping("/download")
    public byte[] download(@RequestParam String fileName, HttpServletResponse response) throws IOException {
        String extension = FilenameUtils.getExtension(fileName);
        switch (extension) {
            case "gif":
                response.setContentType(MediaType.IMAGE_GIF_VALUE);
                break;
            case "jpg":
            case "jpeg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
        }
        response.setHeader("Content-Disposition" , "Attachement; fileName=\"" + fileName + "\"");
        return applicationService.download(fileName);
    }


}