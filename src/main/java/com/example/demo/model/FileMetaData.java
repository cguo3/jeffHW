package com.example.demo.model;


import javax.persistence.*;

@Entity
@Table(name = "file")
public class FileMetaData {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fileId;

    private String fileName;

    private String filePath;

    public FileMetaData() {
    }

    public FileMetaData(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
