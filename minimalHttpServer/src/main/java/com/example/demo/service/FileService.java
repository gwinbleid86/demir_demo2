package com.example.demo.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.stream.Stream;

@Slf4j
@Service
public class FileService {

    private static final String SEMI_PATH = "data/new/";

    @SneakyThrows
    public String readFile(String filename) {
        Path path = Paths.get(SEMI_PATH + filename);
        if (Files.exists(path)) {
            return Files.readString(path);
        }
        return "";
    }

    @SneakyThrows
    public void writeFile(String json, String filename) {
        Path path = Paths.get(SEMI_PATH + filename);
        long count = getCountStringsFromFile(path);

        String newJson = json + "\n" + readFile(filename);

        try {
            Files.delete(path);
        } catch (IOException e) {
            log.info("Method Files.delete(): NoSuchFileException");
        }
        String newFilename = appendCount(filename, count);
        log.info("Using file: {}", newFilename);
        path = Paths.get(SEMI_PATH + newFilename);
        Files.write(path, newJson.getBytes());
    }

    @SneakyThrows
    public String getFilename(String type, String date) {
        Path path = Paths.get(SEMI_PATH);
        try (Stream<Path> input = Files.list(path)) {
            return input
                    .map(e -> e.toString().split("/")[1])
                    .filter(e -> e.startsWith(type))
                    .findFirst()
                    .orElse(String.format("%s-%s-0001.log", type, date));
        } catch (IOException e) {
            log.error("Directory not exist. Create directory '{}'", SEMI_PATH);
            Files.createDirectory(path);
            return getFilename(type, date);
        }
    }

    private long getCountStringsFromFile(Path path) {
        try (Stream<String> input = Files.lines(path)) {
            return input.count();
        } catch (IOException e) {
            log.error("Method getCountStringsFromFile(): NoSuchFileException");
            return 0;
        }
    }

    private String appendCount(String filename, long count) {
        NumberFormat nf = new DecimalFormat("0000");
        String newCount = nf.format(count + 1);
        String str = filename.substring(0, filename.length() - 8);
        return str + newCount + ".log";
    }
}
