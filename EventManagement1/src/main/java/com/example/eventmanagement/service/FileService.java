package com.example.eventmanagement.service;

import java.io.IOException;
import java.nio.file.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String uploadFile(MultipartFile file)
            throws IOException {

        Path directory = Paths.get(uploadDir)
                .toAbsolutePath().normalize();

        Files.createDirectories(directory);

        String originalName = Paths
                .get(file.getOriginalFilename())
                .getFileName().toString();

        String fileName = java.util.UUID.randomUUID()
                + "_" + originalName;

        Path target = directory.resolve(fileName).normalize();

        if (!target.startsWith(directory)) {
            throw new IOException("Invalid file path");
        }

        Files.copy(
            file.getInputStream(),
            target,
            StandardCopyOption.REPLACE_EXISTING
        );

        return fileName;
    }

    public Resource downloadFile(String fileName)
            throws IOException {

        Path directory = Paths.get(uploadDir)
                .toAbsolutePath().normalize();

        Path filePath = directory.resolve(fileName).normalize();

        if (!filePath.startsWith(directory)
                || !Files.isRegularFile(filePath)) {

            throw new IOException("File not found");
        }

        Resource resource = new UrlResource(
                filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new IOException("Cannot read file");
        }

        return resource;
    }
}