package com.example.second_project.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {

    private final FileService fileService;

    private final RabbitProducer rabbitProducer;

    public void run() {
        var paths = fileService.getListFiles(Paths.get("data/new/"));
        paths.forEach(e -> {
            List<String> list = fileService.readFile(e);
            chopped(list, 100).forEach(i -> rabbitProducer.send(i.toString()));
            fileService.moveFile(e.getFileName().toString());
        });

    }

    private <T> List<List<T>> chopped(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<>(
                    list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }

    @SneakyThrows
    public void handleData(String message) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(message);
        String type = getType(node);
        String date = getDate(node);
        String filename = extractName(type, date);
        log.info("Filename: {}", filename);
        fileService.writeFile(filename, message);
    }


    private String extractCount(long count) {
        NumberFormat nf = new DecimalFormat("0000");
        return nf.format(count + 1);
    }

    private String extractName(String type, String date) {
        var count = fileService.getListFiles(Paths.get("data/processed")).stream()
                .map(e -> e.getFileName().toString())
                .filter(e -> e.startsWith(type))
                .count();
        return String.format("%s-%s-%s.log", type, date, extractCount(count));
    }

    private String getDate(JsonNode node) {
        return node.get(0).path("Process").path("Start").path("Date").asText().split("T")[0];
    }

    public String getType(JsonNode node) {
        return node.get(0).path("Type").asText();
    }

}
