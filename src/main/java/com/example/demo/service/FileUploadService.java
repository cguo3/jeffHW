package com.example.demo.service;

import com.example.demo.model.FileMetaData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    String storeFile(MultipartFile file, FileMetaData fileMetaData) throws IOException;

    void saveMetaData(FileMetaData metaData);

}
