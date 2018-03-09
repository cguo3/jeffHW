package com.example.demo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.demo.model.FileMetaData;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;


    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

//    @GetMapping("/")
//    public String listUploadedFiles(Model model) throws IOException {
//
//        model.addAttribute("files", storageService.loadAll().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                        "serveFile", path.getFileName().toString()).build().toString())
//                .collect(Collectors.toList()));
//
//        return "uploadForm";
//    }

//    @GetMapping("/files/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//        Resource file = storageService.loadAsResource(filename);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }

    @PostMapping("/")
    public ResponseEntity<Object> handleFileUpload(@RequestBody MultipartFile file,
                                                   @RequestParam("fileName") String fileName) throws IOException {


        FileMetaData metaData = new FileMetaData(fileName);

        String targetFilePath = fileService.storeFile(file, metaData);
        metaData.setFilePath(targetFilePath);
        fileService.saveMetaData(metaData);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping(path = "{fileId}/metadata")
    public ResponseEntity<FileMetaData> getFileMetaData(@PathVariable Long fileId) {
        FileMetaData fileMetaData = fileService.getMetaData(fileId);
        return new ResponseEntity<FileMetaData>(fileMetaData, HttpStatus.OK);
    }


    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(FileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }


    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException() {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}