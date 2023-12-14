package com.example.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class JsonService {

    public JsonNode getNodeFromXml(String xml) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.readTree(xml.getBytes());
        } catch (IOException e) {
            log.error("XML readTree() exception: {}", e.getMessage());
            return null;
        }
    }

    public String getType(JsonNode node) {
        return node.path("Type").asText();
    }

    public String getDate(JsonNode node) {
        return node.path("Process").path("Start").path("Date").asText().split("T")[0];
    }
}
