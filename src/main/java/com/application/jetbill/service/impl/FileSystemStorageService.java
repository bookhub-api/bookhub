package com.application.jetbill.service.impl;


import com.application.jetbill.exception.FileStorageException;
import com.application.jetbill.exception.ResourceNotFoundException;
import com.application.jetbill.service.StorageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {

    @Value("${storage.location}")
    private String storageLocation;

    private  Path rootLocation;


    @PostConstruct
    @Override
    public void init() {
        if (storageLocation.trim().isEmpty()) {
            throw new FileStorageException("File upload location can not be Empty.");
        }

        rootLocation = Paths.get(storageLocation);

        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new FileStorageException("Cannot store an empty file.", e);
        }

    }

    @Override
    public String storeFile(MultipartFile file) {
        validateFile(file);
        String filename = generateUniqueFilename(file.getOriginalFilename());
        Path destinationFile = resolveDestinationPath(filename);
        saveFile(file, destinationFile);
        return filename;

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new ResourceNotFoundException("Could not read file: " + filename);
        }
    }

    @Override
    public void delete(String filename) {
        Path file = load(filename);
        try {
            FileSystemUtils.deleteRecursively(file);
        } catch (IOException ex) {
            throw new FileStorageException("Can't delete the file.",ex);
        }

    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileStorageException("Cannot store an empty file.");
        }
    }

    private String generateUniqueFilename(String originalFilename) {
        String extension = FilenameUtils.getExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + (extension.isEmpty() ? "" : "." + extension);
    }

    private Path resolveDestinationPath(String filename) {
        Path destinationFile = rootLocation.resolve(filename).normalize();
        if (!destinationFile.getParent().equals(rootLocation)) {
            throw new FileStorageException("Cannot store file outside the designated directory.");
        }
        return destinationFile;
    }
    private void saveFile(MultipartFile file, Path destinationFile) {
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file.", e);
        }
    }
}
