package br.edu.utfpr.imc.controller;

import br.edu.utfpr.imc.dto.ImcResult;
import br.edu.utfpr.imc.model.Person;
import br.edu.utfpr.imc.service.ImcService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/imc")
public class ImcController {

    private final ImcService imcService;

    @Autowired
    public ImcController(ImcService imcService) {
        this.imcService = imcService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<ImcResult> calculateImc(@Valid @RequestBody Person person) {
        ImcResult result = imcService.calculateImc(person);
        return ResponseEntity.ok(result);
    }
}
