package com.diziperest.web.services.concretes;

import com.diziperest.web.services.abstracts.IStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class StorageService implements IStorageService {

    private final Path rootLocation = Paths.get("upload-dir");
    @Override
    public String store(MultipartFile file, String fileName) {
        String uploadedFileName = fileName.toLowerCase().replaceAll(" ", "-")
                + new Date().getTime() +"."
                + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(uploadedFileName));
        }catch (IOException e){
            throw new RuntimeException("Dosya sisteme yüklenemedi.");
        }
        return uploadedFileName;
    }


    @Override
    public void init() {
        if (!Files.exists(rootLocation))
        {
            try {
                Files.createDirectory(rootLocation);
            } catch (IOException e) {
                throw new RuntimeException("Ana dizin sisteme yüklenemedi!");
            }
        }
    }
}