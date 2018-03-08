package com.example.demo.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.demo.model.FileMetaData;
import com.example.demo.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/files")
public class FileController {

    private final FileUploadService fileUploadService;


    @Autowired
    public FileController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
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

        String targetFilePath = fileUploadService.storeFile(file, metaData);
        metaData.setFilePath(targetFilePath);
        fileUploadService.saveMetaData(metaData);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping(path = "{fileId}/metadata")
    public


    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(FileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }


    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException() {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}