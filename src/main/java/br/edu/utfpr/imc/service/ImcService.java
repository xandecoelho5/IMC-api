package br.edu.utfpr.imc.service;

import br.edu.utfpr.imc.dto.ImcResult;
import br.edu.utfpr.imc.model.Classification;
import br.edu.utfpr.imc.model.Person;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ImcService {
    public ImcResult calculateImc(Person person) {
        if (person == null || person.getWeight() == null || person.getHeight() == null) {
            throw new IllegalArgumentException("Pessoa, peso e altura não podem ser nulos");
        }

        double imc = calculateImcValue(person.getWeight(), person.getHeight());
        Classification classification = classifyImc(imc);

        return new ImcResult(imc, classification);
    }

    protected double calculateImcValue(double weight, double height) {
        return Math.round((weight / (height * height)) * 100.0) / 100.0;
    }

    protected Classification classifyImc(double imc) {
        var classifications = Classification.values();
        return Arrays.stream(classifications).filter(c -> imc < c.getMinValue())
                .findFirst()
                .orElse(classifications[classifications.length - 1]);
    }
}