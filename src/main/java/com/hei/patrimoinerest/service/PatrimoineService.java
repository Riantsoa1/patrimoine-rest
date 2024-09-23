package com.hei.patrimoinerest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hei.patrimoinerest.model.Patrimoine;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PatrimoineService {

    private static final String FILE_PATH = "/src/main/resources/patrimoines.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Optional<Patrimoine> getPatrimoine(String id) throws IOException {
        return Files.lines(Paths.get(FILE_PATH))
                .map(line -> {
                    try {
                        return objectMapper.readValue(line, Patrimoine.class);
                    } catch (IOException e) {
                        return null;
                    }
                })
                .filter(p -> p != null && p.id().equals(id))
                .findFirst();
    }

    public Patrimoine savePatrimoine(String id, String possesseur) throws IOException {
        Patrimoine patrimoine = new Patrimoine(id, possesseur, LocalDateTime.now());
        String jsonString = objectMapper.writeValueAsString(patrimoine);
        
        Files.writeString(Paths.get(FILE_PATH), jsonString + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        return patrimoine;
    }
}
