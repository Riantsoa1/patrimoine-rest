package com.hei.patrimoinerest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hei.patrimoinerest.model.Patrimoine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/patrimoine")
public class PatrimoineControler {
    private static final Path FILE_PATH = Paths.get("patrimoine.json");
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/{id}")
    public Patrimoine getPatrimoine(@PathVariable String id) throws IOException {
        if (Files.exists(FILE_PATH)){
            Patrimoine[] patrimoines = objectMapper.readValue(FILE_PATH.toFile(), Patrimoine[].class);
            for (Patrimoine patrimoine : patrimoines){
                if (patrimoine.possesseur().equals(id)){
                    return patrimoine;
                }
            }
        }
        throw new RuntimeException("Patrimoine not found");
    }
}
