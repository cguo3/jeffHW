package com.example.demo.service;

import com.example.demo.model.FileMetaData;
import com.example.demo.repository.FileMetaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class FileServiceImpl implements FileService {

    private final FileMetaDataRepository fileMetaDataRepository;

    @Value( "${file.upload.path}" )
    private String FILE_UPLOAD_PATH;


    @Autowired
    public FileServiceImpl(FileMetaDataRepository fileMetaDataRepository) {
        this.fileMetaDataRepository = fileMetaDataRepository;
    }


    @Override
    public String storeFile(MultipartFile file, FileMetaData fileMetaData) throws IOException {

        InputStream inputStream = file.getInputStream();
        String targetFilePath = FILE_UPLOAD_PATH + fileMetaData.getFileName();
        File targetFile = new File(targetFilePath);

        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(targetFile);

        System.out.println(targetFile);
        System.out.println(fileMetaData.getFileName());
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        return targetFilePath;
    }



    @Override
    public void saveMetaData(FileMetaData metaData) {
        fileMetaDataRepository.save(metaData);
    }

    @Override
    public FileMetaData getMetaData(Long fileId) {
        return fileMetaDataRepository.findOne(fileId);
    }
}
