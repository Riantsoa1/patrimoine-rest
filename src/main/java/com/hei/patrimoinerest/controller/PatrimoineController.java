package com.hei.patrimoinerest.controller;

import com.hei.patrimoinerest.model.Patrimoine;
import com.hei.patrimoinerest.service.PatrimoineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/patrimoines")
public class PatrimoineController {

    private final PatrimoineService patrimoineService;

    @Autowired
    public PatrimoineController(PatrimoineService patrimoineService) {
        this.patrimoineService = patrimoineService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patrimoine> getPatrimoine(@PathVariable String id) throws IOException {
        Optional<Patrimoine> patrimoine = patrimoineService.getPatrimoine(id);
        return patrimoine.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patrimoine> createOrUpdatePatrimoine(@PathVariable String id, @RequestBody String possesseur) throws IOException {
        Patrimoine savedPatrimoine = patrimoineService.savePatrimoine(id, possesseur);
        return ResponseEntity.ok(savedPatrimoine);
    }
}
