package com.example.second_project.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class FileService {

    private static final String DATA_NEW = "data/new/";
    private static final String DATA_PROCESSED = "data/processed/";
    private static final String DATA_OBSERVED = "data/observed/";

    @SneakyThrows
    public List<Path> getListFiles(Path path) {
        try (Stream<Path> input = Files.list(path)) {
            return input.toList();
        } catch (IOException e){
            log.error("Directory not exist. Create directory '{}'", path);
            Files.createDirectory(path);
            return getListFiles(path);
        }
    }

    @SneakyThrows
    public List<String> readFile(Path path) {
        return Files.readAllLines(path);
    }

    @SneakyThrows
    public void moveFile(String filename) {
        try {
            Files.move(
                    Paths.get(DATA_NEW + filename),
                    Paths.get(DATA_OBSERVED + filename),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            log.info("Directory not exist. Create directory '{}'", DATA_OBSERVED);
            Files.createDirectory(Paths.get(DATA_OBSERVED));
            moveFile(filename);
        }
    }

    @SneakyThrows
    public void writeFile(String filename, String message) {
        Path path = Paths.get(DATA_PROCESSED + filename);
        Files.write(path, message.getBytes());
    }
}
