package com.example.demo.service;

import com.example.demo.util.UnirestUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {

    private final FileService fileService;

    private final JsonService jsonService;

    @SneakyThrows
    public void createFile(String xml) {
        JsonNode node = jsonService.getNodeFromXml(xml);
        String type = jsonService.getType(node);
        String date = jsonService.getDate(node);
        String filename = fileService.getFilename(type, date);
        fileService.writeFile(node.toString(), filename);

        try(UnirestUtil unirestUtil = new UnirestUtil()){
            unirestUtil.send();
        }
    }
}
