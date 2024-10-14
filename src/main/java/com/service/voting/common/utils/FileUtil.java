package com.service.voting.common.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.service.voting.common.exception.ResourceNotFoundException;

@Service
public class FileUtil {
    private final Path defaultFileStorageLocation;

    @Autowired
    public FileUtil(Environment env) {
        this.defaultFileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir-rpt", "./uploaded/report"))
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.defaultFileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        String[] fileNameParts = fileName.split("\\.");

        return fileNameParts[fileNameParts.length - 1];
    }

    public String store(MultipartFile file, String customPath, String customFileName) {
        String fileName = (customFileName != null && !customFileName.isEmpty()) ? customFileName : file.getOriginalFilename();
        Path fileStorageLocation = (customPath != null && !customPath.isEmpty()) ? Paths.get(customPath).toAbsolutePath().normalize() : this.defaultFileStorageLocation;

        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Files.createDirectories(fileStorageLocation);

            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public void deleteFile(String fileName, String customPath) {
        Path fileStorageLocation = (customPath != null && !customPath.isEmpty()) ? Paths.get(customPath).toAbsolutePath().normalize() : this.defaultFileStorageLocation;
        Path targetLocation = fileStorageLocation.resolve(fileName);

        try {
            if (!Files.exists(targetLocation)) {
                throw new ResourceNotFoundException("File " + fileName + " does not exist.");
            }

            Files.delete(targetLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Could not delete file " + fileName + ". Please try again!", ex);
        }
    }
}