package com.example.demo.service;

import com.example.demo.model.FileMetaData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    String storeFile(MultipartFile file, FileMetaData fileMetaData) throws IOException;

    void saveMetaData(FileMetaData metaData);

    FileMetaData getMetaData(Long fileId);

}
