package com.diziperest.web.services.abstracts;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

    String store(MultipartFile file, String fileName);

    void init();
}
